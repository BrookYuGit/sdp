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
    return false //  ??????????????????
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
    let export_excel_fields = [{ prop: '#', title: '??????', width: 10 }]
    fields.forEach((prop) => {
      let field = self.columns.find((ele) => ele.prop === prop)
      if (!field) {
        self.$baseMessage('???????????????:' + prop)
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
        self.$baseMessage('????????????:' + err, 'error')
        self.listLoading = false
      })
  },
  handleEdit(self, row) {
    if (self.selectRows.length == 0) {
      self.$baseMessage('?????????????????????', 'error')
      return
    }
    if (self.selectRows.length > 1) {
      self.$baseMessage('????????????????????????', 'error')
      return
    }
    self.$refs['edit'].showEdit(self.selectRows[0])
  },
  handleBatchEdit(self, row) {
    if (self.selectRows.length == 0) {
      self.$baseMessage('???????????????????????????', 'error')
      return
    }
    self.$refs['batchEdit'].showEdit(self.selectRows)
  },
  handleUpdateRootPath(self, doExecute) {
    let count = 0
    let split = '/'
    let found_split = false
    for(let item in self.selectRows) {
      item = self.selectRows[item]
      if (item.root_path) {
        if (item.root_path.indexOf('(root)') == 0) {
          count++;
        }
        if (item.root_path.indexOf(split) >= 0) {
          found_split = true
        }
      }
    }
    if (!found_split) {
      split = '\\'
    }
    if (count == 0) {
      self.$baseMessage('?????????????????????????????????????????????????????????????????????', 'error')
      return
    }
    self.$prompt('???????????????', '??????', {
      confirmButtonText: '??????',
      cancelButtonText: '??????',
    })
      .then(({ value }) => {
        value = value.trim()
        if (value) {
          for(let item in self.selectRows) {
            item = self.selectRows[item]
            if (item.root_path && item.root_path.indexOf('(root)') == 0) {
              let path = item.root_path.substring('(root)'.length)
              if (path.indexOf(split) == 0 && value.substring(value.length - 1) == split) {
                item.root_path = value + path.substring(1)
              } else if (path.indexOf(split) != 0 && value.substring(value.length - 1) != split) {
                item.root_path = value + split + path
              } else {
                item.root_path = value + path
              }
            }
            self.updateProject(item)
          }
        }
      })
  },
  handleExecute(self, doExecute) {
    let workspaceName = self.queryForm.workspace_name
    if (!workspaceName) {
      if (self.selectRows.length == 0) {
        self.$baseMessage('??????????????????????????????????????????????????????', 'error')
        return
      }
      workspaceName = self.selectRows[0].workspace_name
    }
    self.$baseConfirm(
      '?????????????????????' + workspaceName + '???????????????????????????',
      null,
      () => {
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
                '????????????,??????' +
                  self.$baseDiffTsp(new Date(), self.execute_tsp),
                '??????',
                'error',
                undefined,
                24 * 60 * 60 * 1000
              )
              return
            }
            self.$baseNotify(
              '????????????,??????' + self.$baseDiffTsp(new Date(), self.execute_tsp),
              '??????',
              'success',
              undefined,
              24 * 60 * 60 * 1000
            )
          })
          .catch((err) => {
            console.log('err', err)
            if ((err + '').indexOf('vue-admin-beautiful??????????????????:') >= 0) {
              err = JSON.parse(
                err.substring('vue-admin-beautiful??????????????????:'.length)
              )
              err.message = err.msg
            }
            self.$baseNotify(
              '????????????,??????' +
                self.$baseDiffTsp(new Date(), self.execute_tsp) +
                ',?????????' +
                err.message,
              '??????',
              'error',
              undefined,
              24 * 60 * 60 * 1000
            )
            self.executeLoading = false
          })
      }
    )
  },
  onWorkspaceChange(self, v) {
    // console.log('onWorkspaceChange', self, v)
    setStorageValue('workspace_name', v)
    //self.queryForm.workspace_name = v
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
        '????????????????????????????????????',
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
      self.$baseMessage('??????????????????', 'error')
      return
    }
    const ids = selectRows.map((item) => item.id)
    if (!disableConfirm) {
      self.$baseConfirm('??????????????????????????????', null, () => {
        self.listLoading = true
        self
          .doDelete({ id: ids })
          .catch((err) => {
            self.fetchWithHistoryData()
          })
          .then(({ data: count }) => {
            if (count <= 0) {
              self.$baseMessage('????????????', 'error')
              self.fetchWithHistoryData()
              return
            }
            if (count < selectRows.length) {
              self.$baseMessage(
                '????????????????????????' +
                  count +
                  '????????????' +
                  (selectRows.length - count) +
                  '???,?????????...',
                'success'
              )
              self.rowsForDelete = selectRows.slice(count)
              setTimeout(self.handleDeleteMore, 100)
              return
            }
            self.$baseMessage('??????????????????' + count + '???', 'success')
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
            self.$baseMessage('????????????', 'error')
            self.fetchWithHistoryData()
            return
          }
          if (count < selectRows.length) {
            self.$baseMessage(
              '????????????????????????' +
                count +
                '????????????' +
                (selectRows.length - count) +
                '???,?????????...',
              'success'
            )
            self.rowsForDelete = selectRows.slice(count)
            setTimeout(self.handleDeleteMore, 100)
            return
          }
          self.$baseMessage('??????????????????' + count + '???', 'success')
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
    setStorageValue('workspace_name', queryForm.workspace_name)
    delete queryForm.workspace_name
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
      queryForm.workspace_name = getStorageValue('workspace_name')
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
    } else {
      self.queryForm.workspace_name = getStorageValue('workspace_name')
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
