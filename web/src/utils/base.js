/* eslint-disable */

import { getStorageValue, setStorageValue } from '@/utils'

import dayjs from 'dayjs'

export function fixBlankFileds(obj) {
  for (let p in obj) {
    if (obj[p] === '') {
      delete obj[p]
    }
  }
  return obj
}

export default {
  fixColumns(self, columnsOrderStore) {
    if (!columnsOrderStore) {
      columnsOrderStore =
        JSON.parse(getStorageValue('config_order_' + self.module)) || ''
    }

    let columnsOrderMap = new Object()
    if (columnsOrderStore) {
      columnsOrderStore.forEach((item) => {
        if (item.order) {
          columnsOrderMap[item.prop] = {
            order: item.order,
            orderIndex: item.orderIndex,
          }
        }
      })
    }

    let columnId = 1
    self.columns.forEach((item) => {
      item.id = columnId++
      item.width = item.width || 100
      item.sortable = 'sortable' in item ? item.sortable : true
      item.resizable = true
      let columnOrder = columnsOrderMap[item.prop]
      if (columnOrder) {
        item.order = columnOrder.order
        item.orderIndex = columnOrder.orderIndex
      }
      // if (item.isTextFilter) {
      //   item.filters = [{ data: '' }, { data: '' }]
      //   item.filterMethod = self.filterInputMethod
      // }
    })
    self.sortMethod()
  },
  sortMethod(self, a) {
    if (a) {
      let column = self.columns.find((item) => item.prop === a.property)
      if (column.order === 'desc' || column.order === 'desc/gbk') {
        column.order = null
        column.orderIndex = null
      } else if (column.sort_by_gbk) {
        if (column.order === 'asc/gbk') {
          column.order = 'desc/gbk'
        } else {
          column.order = 'asc/gbk'
        }
      } else {
        if (column.order === 'asc') {
          column.order = 'desc'
        } else {
          column.order = 'asc'
        }
      }
      if (column.order) {
        if (!column.orderIndex) {
          column.orderIndex = 0
          self.columns.forEach((item) => {
            if (item.order && item.orderIndex > column.orderIndex) {
              column.orderIndex = item.orderIndex
            }
          })
          column.orderIndex++
        }
      } else {
        column.column_index = null
      }
      setStorageValue(
        'config_order_' + self.module,
        JSON.stringify(self.columns)
      )
    }
    let sortColumns = []
    self.columns.forEach((item) => {
      if (item.order) {
        sortColumns.push(item)
      }
    })
    let sortCondition = []
    sortColumns
      .sort(function compareFunction(param1, param2) {
        return param1.orderIndex - param2.orderIndex
      })
      .forEach((item) => {
        item.orderIndex = sortCondition.length + 1
        let sortItem = new Object()
        sortItem[item.prop] = item.order
        sortCondition.push(sortItem)
      })

    if (!self.queryForm) {
      self.queryForm = {}
    }
    if (!self.queryForm.query_options) {
      self.queryForm.query_options = {}
    }
    self.queryForm.query_options.order_by = sortCondition
    if (a) {
      self.fixColumns(self.columns)
      if (self.$refs.plxTable.refreshColumn) {
        self.$refs.plxTable.refreshColumn()
      }
      // self.fetchData();
    }
  },
  handleUseRsa(self, v) {
    self.useRsa = v
    window.use_rsa = v
    setStorageValue('use_rsa', window.use_rsa ? 1 : 0)
  },
  onTableFilterChange() {},
  onRowSelect() {},
  onTableSelectionChange(self, selection) {
    self.selectRows = selection
  },
  beforeSync(self, file) {
    self.files = file
    return false //  取消默认上传
  },
  tableSortChange(self) {},
  setSelectRows(self, val) {
    self.selectRows = val
  },
  handleAdd(self, row) {
    self.$refs['edit'].showEdit(row)
  },
  handleShowDownload(self, queryForm, fields, doExportExcel, json_field_map) {
    fields = []
    for (let p in self.columns) {
      let item = self.columns[p]
      if (item.prop == '#') {
        continue
      }
      if (item.prop == 'id') {
        continue
      }
      fields.push(self.columns[p].prop)
    }

    self.listLoading = true
    let export_excel_fields = [{ prop: '#', title: '序号', width: 10 }]
    fields.forEach((prop) => {
      let field = self.columns.find((ele) => ele.prop === prop)
      if (!field) {
        self.$baseMessage('未找到属性:' + prop)
        return
      }
      export_excel_fields.push({
        prop,
        title: field.label,
        width: field.width / 10,
      })
    })
    let param = {
      ...fixBlankFileds(queryForm),
      pageNo: undefined,
      pageSize: undefined,
      export_excel_fields,
      // template_name:'excelExportTemplatesNoStyle.xls',
    }
    if (json_field_map) {
      param.json_field_map = json_field_map
    }
    if (self.selectRows.length) {
      param.id_list = self.selectRows.map((item) => item.id)
    }
    doExportExcel(self, param)
      .then((data) => {
        self.listLoading = false
        let link = document.createElement('a')
        let blob = new Blob([data], {
          type: 'application/vnd.ms-excel',
        })
        link.href = window.URL.createObjectURL(blob)
        link.download =
          self.title + '_' + dayjs().format('YYYYMMDDHHmmss') + '.xls'
        document.body.appendChild(link)
        link.click()
      })
      .catch((err) => {
        self.$baseMessage('导出失败:' + err, 'error')
        self.listLoading = false
      })
  },
  handleEdit(self, row) {
    if (self.selectRows.length == 0) {
      self.$baseMessage('请选择一条记录', 'error')
      return
    }
    if (self.selectRows.length > 1) {
      self.$baseMessage('只能选择一条记录', 'error')
      return
    }
    self.$refs['edit'].showEdit(self.selectRows[0])
  },
  handleBatchEdit(self, row) {
    if (self.selectRows.length == 0) {
      self.$baseMessage('请至少选择一条记录', 'error')
      return
    }
    self.$refs['batchEdit'].showEdit(self.selectRows)
  },
  handleExecute(self, doExecute) {
    let workspaceName = self.queryForm.workspace_name
    if (!workspaceName) {
      if (self.selectRows.length == 0) {
        self.$baseMessage('必须设置工作区过滤条件或选择一条数据', 'error')
        return
      }
      workspaceName = self.selectRows[0].workspace_name
    }
    self.$baseConfirm('你确定要执行吗', null, () => {
      self.executeLoading = true
      let db_password = getStorageValue('db_password_' + workspaceName)
      if (!db_password) {
        db_password = ''
      }
      self.execute_tsp = new Date()
      self
        .doExecute({
          name: JSON.stringify({
            workspace_name: workspaceName,
            db_password,
          }),
        })
        .then(({ data: count }) => {
          self.executeLoading = false
          if (count <= 0) {
            self.$baseNotify(
              '操作失败,用时' + self.$baseDiffTsp(new Date(), self.execute_tsp),
              '执行',
              'error',
              undefined,
              24 * 60 * 60 * 1000
            )
            return
          }
          self.$baseNotify(
            '操作成功,用时' + self.$baseDiffTsp(new Date(), self.execute_tsp),
            '执行',
            'success',
            undefined,
            24 * 60 * 60 * 1000
          )
        })
        .catch((err) => {
          console.log('err', err)
          if ((err + '').indexOf('vue-admin-beautiful请求异常拦截:') >= 0) {
            err = JSON.parse(
              err.substring('vue-admin-beautiful请求异常拦截:'.length)
            )
            err.message = err.msg
          }
          self.$baseNotify(
            '操作失败,用时' +
              self.$baseDiffTsp(new Date(), self.execute_tsp) +
              ',错误：' +
              err.message,
            '执行',
            'error',
            undefined,
            24 * 60 * 60 * 1000
          )
          self.executeLoading = false
        })
    })
  },
  onRowClick(self, a) {
    if (self.selectRows.length > 1) {
      let found = false
      self.selectRows.forEach((item) => {
        if (item.id === a.id) {
          found = true
        }
      })
      if (found) {
        return
      }
      self.$baseConfirm(
        '是否要取消其他选择状态？',
        null,
        () => {
          if (self.$refs.plxTable.clearSelection) {
            self.$refs.plxTable.clearSelection()
          }
          if (self.$refs.plxTable.toggleRowSelection) {
            self.$refs.plxTable.toggleRowSelection(a)
          }
        },
        () => {
          if (self.$refs.plxTable.toggleRowSelection) {
            self.$refs.plxTable.toggleRowSelection(a)
          }
        }
      )
    } else {
      if (self.$refs.plxTable.clearSelection) {
        self.$refs.plxTable.clearSelection()
      }
      if (self.$refs.plxTable.toggleRowSelection) {
        self.$refs.plxTable.toggleRowSelection(a)
      }
    }
  },
  handleDeleteMore(self) {
    self.handleDelete(self, {}, self.rowsForDelete, true)
  },
  handleDelete(self, row, selectRows, disableConfirm) {
    if (row.id) {
      selectRows = [row]
    }
    if (!selectRows) {
      selectRows = self.selectRows
    }
    if (selectRows.length == 0) {
      self.$baseMessage('未选中任何行', 'error')
      return
    }
    const ids = selectRows.map((item) => item.id)
    if (!disableConfirm) {
      self.$baseConfirm('你确定要删除选中项吗', null, () => {
        self.listLoading = true
        self
          .doDelete({ id: ids })
          .catch((err) => {
            self.fetchWithHistoryData()
          })
          .then(({ data: count }) => {
            if (count <= 0) {
              self.$baseMessage('操作失败', 'error')
              self.fetchWithHistoryData()
              return
            }
            if (count < selectRows.length) {
              self.$baseMessage(
                '部分操作成功，共' +
                  count +
                  '条，剩余' +
                  (selectRows.length - count) +
                  '条,继续中...',
                'success'
              )
              self.rowsForDelete = selectRows.slice(count)
              setTimeout(self.handleDeleteMore, 100)
              return
            }
            self.$baseMessage('操作成功，共' + count + '条', 'success')
            self.fetchWithHistoryData()
          })
      })
    } else {
      self.listLoading = true
      self
        .doDelete({ id: ids })
        .catch((err) => {
          self.fetchWithHistoryData()
        })
        .then(({ data: count }) => {
          if (count <= 0) {
            self.$baseMessage('操作失败', 'error')
            self.fetchWithHistoryData()
            return
          }
          if (count < selectRows.length) {
            self.$baseMessage(
              '部分操作成功，共' +
                count +
                '条，剩余' +
                (selectRows.length - count) +
                '条,继续中...',
              'success'
            )
            self.rowsForDelete = selectRows.slice(count)
            setTimeout(self.handleDeleteMore, 100)
            return
          }
          self.$baseMessage('操作成功，共' + count + '条', 'success')
          self.fetchWithHistoryData()
        })
    }
  },
  handleSizeChange(self, val) {
    self.queryForm.pageSize = val
    window.gridConfig_pageSize = val
    setStorageValue('gridConfig_pageSize', val)
    self.fetchData()
  },
  handleCurrentChange(self, val) {
    self.queryForm.pageNo = val
    self.fetchData()
  },
  handleQuery(self) {
    let queryForm = { ...self.queryForm }
    setStorageValue(
      'query_form_' + self.module,
      JSON.stringify(fixBlankFileds(queryForm))
    )
    self.queryForm.pageNo = 1
    self.queryForm.pageSize = window.gridConfig_pageSize || 10
    self.fetchAllData()
  },
  filterHistoryMethod(self, { option, row }) {
    let ret = row.name === option.data
    return ret
  },
  filterInputMethod(self, option, row, column) {
    let ret = true
    try {
      let value = ((option || {}).data || '').trim()
      const property = column['property']
      ret = value && (row[property] + '').indexOf(value) >= 0
    } catch (ex) {
      console.error('filterInputMethod error', ex)
    }

    return ret
  },
  fetchAllData(self) {
    let queryForm =
      JSON.parse(getStorageValue('query_form_' + self.module)) || ''
    if (queryForm) {
      let notFound = false
      for (let p in self.queryForm) {
        if (p == 'query_options') {
          continue
        }
        if (
          p == 'pageSize' ||
          p == 'page_size' ||
          p == 'pageNo' ||
          p == 'page_no'
        ) {
          continue
        }
        if (p in queryForm && queryForm[p]) {
          self.queryForm[p] = queryForm[p]
        }
      }
    }
    window.gridConfig_pageSize = parseInt(
      getStorageValue('gridConfig_pageSize') || '10'
    )
    self.queryForm.pageSize = window.gridConfig_pageSize
    self.fetchAllData()
  },
  fetchWithHistoryData(self) {
    self.fetchWithCount()
    self.fetchDataHistory()
  },
  fetchWithHistoryDataByUpdate(self) {
    let scrollLeft = self.scrollLeft
    let scrollTop = self.scrollTop
    self.fetchWithCount(() => {
      if (scrollLeft || scrollTop) {
        if (self.$refs.plxTable.pagingScrollTopLeft) {
          self.$refs.plxTable.pagingScrollTopLeft(scrollTop, scrollLeft)
        }
      }
    })
    self.fetchDataHistory()
  },
  fetchDataHistory(self) {},
  finishFetchData(self, data, cb) {
    self.list = data
    if (self.$refs.plxTable.reloadData) {
      self.$refs.plxTable.reloadData(self.list)
    }
    self.listLoading = false
    if (self.scrollLeft) {
      if (self.$refs.plxTable.pagingScrollTopLeft) {
        self.$refs.plxTable.pagingScrollTopLeft(0, self.scrollLeft)
      }
    }
    if (cb) {
      cb()
    }
  },
  handleShow(self, data) {
    self.extraImportExcelProps = {
      sheet_name: '',
      name: 'import' + Math.random(),
      keys: {},
    }
    self.$refs['importExcel'].handleShow(data, self)
  },
}
