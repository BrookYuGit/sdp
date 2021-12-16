<template>
  <el-row>
    <el-col>
      <vab-query-form>
        <el-form
          ref="form"
          :model="queryForm"
          :inline="true"
          @submit.native.prevent
        >
          <el-form-item>
            <el-select
              v-model="queryForm.name"
              placeholder="工作区"
              filterable
              style="width: 100%"
              :clearable="true"
            >
              <el-option
                v-for="item in workspaceList"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-search"
              type="primary"
              native-type="submit"
              @click="handleQuery"
            >
              查询
            </el-button>
          </el-form-item>
          <el-button type="primary" @click="handleSetPassword">
            设置密码(不保存到服务器)
          </el-button>
          <!-- <el-button type="primary" @click="handleShowDownload">
            导出
          </el-button> -->
          <el-button type="primary" @click="handleClone">克隆</el-button>
          <!-- <el-button type="primary" @click="handleCloneWorkspace">
            克隆工程全部文件
          </el-button> -->
          <el-button type="primary" @click="handleShow">导入</el-button>
          <el-button type="primary" @click="handleExport">导出</el-button>
          <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
            添加
          </el-button>
          <el-button type="primary" @click="handleEdit">修改</el-button>
          <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
            删除
          </el-button>
          <!-- <el-button type="primary" v-if="!showRight" @click="handleShowRight(true)">
            显示历史
          </el-button> -->
          <!-- <el-button type="primary" v-if="showRight" @click="handleShowRight(false)">
            隐藏历史
          </el-button> -->
          <el-button v-if="!useRsa" type="primary" @click="handleUseRsa(true)">
            加密传输
          </el-button>
          <el-button v-if="useRsa" type="primary" @click="handleUseRsa(false)">
            非加密传输
          </el-button>
        </el-form>

        <vab-query-form-left-panel></vab-query-form-left-panel>
        <vab-query-form-right-panel></vab-query-form-right-panel>
      </vab-query-form>
    </el-col>
    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
      <div class="table-container">
        <el-table
          ref="plxTable"
          v-loading="listLoading"
          :stripe="true"
          :show-header-overflow="true"
          :height="tableHeight"
          :highlight-current-row="true"
          :filter-change="onTableFilterChange"
          :data="list"
          border
          show-overflow
          @table-body-scroll="onTableBodyScroll"
          @select="onRowSelect"
          @row-click="onRowClick"
          @selection-change="onTableSelectionChange"
        >
          <el-table-column
            type="selection"
            width="60"
            fixed="left"
          ></el-table-column>
          <el-table-column
            v-for="item in columns"
            :key="item.id"
            :show-overflow-tooltip="true"
            :resizable="true"
            :field="item.prop"
            :width="item.width"
            :title="item.label"
            :prop="item.prop"
            :label="item.label"
          ></el-table-column>
        </el-table>

        <el-pagination
          :background="background"
          :current-page="queryForm.pageNo"
          :layout="layout"
          :page-size="queryForm.pageSize"
          :page-sizes="[5, 10, 50, 100, 10000]"
          :total="total"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        ></el-pagination>
      </div>
    </el-col>
    <table-edit
      ref="edit"
      :module="module"
      :columns="columns"
      :shop-list="shopList"
      @fetch-data="fetchWithHistoryData"
    ></table-edit>
    <edit-import
      ref="editImport"
      @fetch-data="fetchWithHistoryData"
    ></edit-import>
  </el-row>
</template>

<script>
  import { createRequest } from '@/api/request'

  import { fixBlankFileds } from '@/utils'

  import { saveAs } from '@/utils'

  import { getStorageValue, setStorageValue } from '@/utils'

  import base from '@/utils/base'

  import TableEdit from './components/Edit'
  import EditImport from './components/EditImport'

  export default {
    name: 'SdpWorkspace',
    components: {
      EditImport,
      TableEdit,
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger',
        }
        return statusMap[status]
      },
    },
    data() {
      let module = 'sdp_workspace'
      return {
        base: undefined,
        title: '项目',
        module,
        getList: createRequest(module, 'list'),
        countList: createRequest(module, 'count'),
        doDelete: createRequest(module, 'delete'),
        doEdit: createRequest(module, 'update'),
        doExportExcel: createRequest(module, 'export_excel'),
        doClone: createRequest(module, 'clone'),
        doCloneWorkspace: createRequest(module, 'clone_workspace'),
        getProjectList: createRequest('sdp_project', 'list'),
        getWorkspaceList: createRequest('sdp_workspace', 'list'),
        getWorkspaceConfigList: createRequest('sdp_workspace_config', 'list'),
        getTemplateList: createRequest('sdp_template', 'list'),
        getSqlList: createRequest('sdp_sql', 'list'),
        without_history: true,
        window: window,
        useRsa: window.use_rsa,
        tableHeight: 550,
        imgShow: true,
        deptFilters: [],
        orgFilters: [],
        orgList: [],
        shopList: [],
        stationList: [],
        companyList: [],
        projectList: [],
        workspaceList: [],
        list: [],
        listHistory: [],
        listLoading: false,
        statusList: [],
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        background: true,
        selectRows: [],
        queryForm: {
          pageNo: 1,
          pageSize: 100,
          name: null,
        },

        // height: 0,
        tableData: [],
        columns: [],
        columnsHistory: [],
      }
    },
    computed: {},
    created() {
      this.columns = [
        // {
        //   prop: 'id',
        //   label: '编号',
        //   width: 100,
        // },
        {
          prop: 'name',
          label: '工作区名称',
          width: 200,
        },
        {
          prop: 'db_host',
          label: '数据库主机',
          width: 200,
        },
        {
          prop: 'db_port',
          label: '数据库端口',
        },
        {
          prop: 'db_database',
          label: '数据库',
          width: 150,
        },
        {
          prop: 'db_username',
          label: '数据库用户名',
        },
        {
          prop: 'db_password',
          label: '数据库密码',
        },
        {
          prop: 'local_db_password',
          label: '本地数据库密码',
        },
        {
          prop: 'db_classname',
          label: '数据库驱动类名',
        },
        {
          prop: 'remark',
          label: '备注',
          width: 300,
        },
      ]

      this.initBase('fetchAllData')
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      initBase(cb, a, b, c) {
        this.$nextTick(() => {
          if (!this.base) {
            this.base = base
            base.fixColumns(this)
          }
          try {
            base[cb](this, a, b, c)
          } catch (ex) {
            console.error('ex', ex)
            console.error('not found cb:', cb, this.base, this.$refs, this)
          }
        })
      },
      fixColumns(a) {
        this.initBase('fixColumns', a)
      },
      finishFetchData(a) {
        this.initBase('finishFetchData', a)
      },
      handleDeleteMore() {
        this.initBase('handleDeleteMore')
      },
      fetchDataHistory() {
        this.initBase('fetchDataHistory')
      },
      sortMethod(a) {
        this.initBase('sortMethod', a)
      },
      fetchWithHistoryData() {
        this.initBase('fetchWithHistoryData')
      },
      onRowSelect() {
        this.initBase('onRowSelect')
      },
      onTableFilterChange() {
        this.initBase('onTableFilterChange')
      },
      onRowClick(a, b, c) {
        this.initBase('onRowClick', a)
      },
      onTableSelectionChange(selection) {
        this.initBase('onTableSelectionChange', selection)
      },
      handleCurrentChange(val) {
        this.initBase('handleCurrentChange', val)
      },
      handleSizeChange(val) {
        this.initBase('handleSizeChange', val)
      },
      handleQuery() {
        this.initBase('handleQuery')
      },
      handleAdd() {
        this.initBase('handleAdd')
      },
      handleEdit(row) {
        this.initBase('handleEdit', row)
      },
      handleDelete(row) {
        this.initBase('handleDelete', row)
      },
      handleShowRight(v) {
        this.initBase('handleShowRight', v)
      },
      handleUseRsa(v) {
        this.initBase('handleUseRsa', v)
      },
      filterInputMethod({ option, row, column }) {
        return this.base.filterInputMethod(this, option, row, column)
      },
      handleShowDownload() {
        let fields = []
        this.initBase(
          'handleShowDownload',
          this.queryForm,
          fields,
          doExportExcel
        )
      },
      fetchAllData() {
        this.listLoading = true

        //获得站点列表
        this.getProjectList({})
          .then((data) => {
            this.projectList = data.data.map((item) => ({
              label: item.name,
              value: item.name,
              data: item,
            }))
            this.projectList = this.projectList.sort(function compareFunction(
              p1,
              p2
            ) {
              return p1.label.localeCompare(p2.label, 'zh')
            })
            return data
          })
          .then((data) => {
            return this.getWorkspaceList({}).then((data) => {
              this.workspaceList = data.data.map((item) => ({
                label: item.name,
                value: item.name,
                data: item,
              }))
              this.workspaceList = this.workspaceList.sort(
                function compareFunction(p1, p2) {
                  return p1.label.localeCompare(p2.label, 'zh')
                }
              )
            })
          })
          .then(() => {
            this.fetchWithHistoryData()
          })
          .catch((err) => {
            this.listLoading = false
          })
      },
      fetchWithCount() {
        this.listLoading = true
        this.countList({ ...fixBlankFileds(this.queryForm) })
          .then(({ data: totalCount }) => {
            this.total = totalCount
            this.fetchData()
          })
          .catch((err) => {
            this.listLoading = false
          })
      },
      onTableBodyScroll(v) {
        this.scrollLeft = v.scrollLeft
      },
      fetchData() {
        this.listLoading = true
        this.selectRows = []

        this.queryForm.query_options = {
          ...(this.queryForm.query_options || {}),
          order_by: [{ name: 'asc' }],
          like: {},
        }
        let form = { ...fixBlankFileds(this.queryForm) }
        for (let p in form) {
          if (p.endsWith('_like') && form[p]) {
            form.query_options.like[p.replace('_like', '')] =
              '%' + form[p] + '%'
            delete form[p]
          }
        }

        this.getList(form)
          .catch((err) => {
            this.listLoading = false
          })
          .then(({ data }) => {
            data.forEach((element) => {
              let password = getStorageValue('db_password_' + element.name)
              if (password) {
                element.local_db_password = '已设置'
              }
            })
            this.finishFetchData(data)
          })
      },
      handleShow(data) {
        this.$refs['editImport'].handleShow(data, this)
      },
      handleClone(row, selectRows) {
        if (this.selectRows.length != 1) {
          self.$baseMessage('请先选择一条数据', 'error')
          return
        }
        let self = this
        this.$prompt('请输入新名称', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        })
          .then(({ value }) => {
            value = value.trim()
            this.listLoading = true
            this.doClone({ name: value, id: this.selectRows[0].id })
              .then(({ data: count }) => {
                if (count <= 0) {
                  self.$baseMessage('操作失败', 'error')
                  self.fetchWithHistoryData()
                  return
                }
                self.$baseMessage('操作成功', 'success')
                self.fetchWithHistoryData()
              })
              .catch((err) => {
                self.fetchWithHistoryData()
              })
          })
          .catch((err) => {
            console.error('err', err)
            this.$message({
              type: 'info',
              message: '取消输入:' + err,
            })
          })
      },
      handleCloneWorkspace(row, selectRows) {
        if (this.selectRows.length != 1) {
          self.$baseMessage('请先选择一条数据', 'error')
          return
        }
        let self = this
        this.$prompt('请输入新名称', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        })
          .then(({ value }) => {
            value = value.trim()
            this.listLoading = true
            this.doCloneWorkspace({ name: value, id: this.selectRows[0].id })
              .then(({ data: count }) => {
                if (count <= 0) {
                  self.$baseMessage('操作失败', 'error')
                  self.fetchWithHistoryData()
                  return
                }
                self.$baseMessage('操作成功', 'success')
                self.fetchWithHistoryData()
              })
              .catch((err) => {
                self.fetchWithHistoryData()
              })
          })
          .catch((err) => {
            console.error('err', err)
            this.$message({
              type: 'info',
              message: '取消输入:' + err,
            })
          })
      },
      handleExport() {
        let self = this
        if (self.selectRows.length == 0) {
          self.$baseMessage('未选中任何行', 'error')
          return
        }
        let workspaceName = self.selectRows[0].name
        let datas = { sdp_workspace: [{ ...self.selectRows[0], id: null }] }
        self.listLoading = true
        this.getWorkspaceConfigList({
          workspace_name: workspaceName,
          query_options: {
            order_by: [{ workspace_name: 'asc' }, { name: 'asc' }],
          },
        })
          .then((data) => {
            datas['sdp_workspace_config'] = data.data.map((item) => ({
              ...item,
              id: null,
            }))
            return this.getProjectList({
              workspace_name: workspaceName,
              query_options: {
                order_by: [{ workspace_name: 'asc' }, { name: 'asc' }],
              },
            })
          })
          .then((data) => {
            datas['sdp_project'] = data.data.map((item) => ({
              ...item,
              id: null,
            }))
            return this.getTemplateList({
              workspace_name: workspaceName,
              query_options: {
                order_by: [
                  { workspace_name: 'asc' },
                  { project_name: 'asc' },
                  { name: 'asc' },
                  { file_type: 'asc' },
                  { project: 'asc' },
                  { package_name: 'asc' },
                ],
              },
            })
          })
          .then((data) => {
            datas['sdp_template'] = data.data.map((item) => {
              item = { ...item, id: null }
              let itemNames = ['file_template', 'remark']
              itemNames.forEach((itemName) => {
                let lines = item[itemName]
                let destLines = []
                if (lines) {
                  lines = lines.split('\n')
                  for (let line in lines) {
                    line = lines[line]
                    destLines.push(line)
                  }
                  item[itemName] = destLines
                }
              })
              itemNames = ['extra_info']
              itemNames.forEach((itemName) => {
                let lines = item[itemName]
                if (lines) {
                  try {
                    item[itemName] = JSON.parse(lines)
                  } catch (ex) {}
                }
              })
              return item
            })
            return this.getSqlList({
              workspace_name: workspaceName,
              query_options: {
                order_by: [
                  { workspace_name: 'asc' },
                  { table_name: 'asc' },
                  { parameter_catalog: 'asc' },
                  { parameter_catalog_type: 'asc' },
                  { name: 'asc' },
                ],
              },
            })
          })
          .then((data) => {
            datas['sdp_sql'] = data.data.map((item) => {
              item = { ...item, id: null }
              let itemNames = ['parameter_sql', 'java_imports', 'remarks']
              itemNames.forEach((itemName) => {
                let lines = item[itemName]
                let destLines = []
                if (lines) {
                  lines = lines.split('\n')
                  for (let line in lines) {
                    line = lines[line]
                    destLines.push(line)
                  }
                  item[itemName] = destLines
                }
              })
              itemNames = ['extra_info']
              itemNames.forEach((itemName) => {
                let lines = item[itemName]
                if (lines) {
                  try {
                    item[itemName] = JSON.parse(lines)
                  } catch (ex) {}
                }
              })
              return item
            })
          })
          .then((data) => {
            self.listLoading = false
            let result = {}
            result[workspaceName] = datas

            result = JSON.stringify(result, null, 2)
            var file = new File([result], 'sdp_' + workspaceName + '.txt', {
              type: 'text/plain;charset=utf-8',
            })
            saveAs()(file)
          })
      },
      handleSetPassword() {
        let self = this
        if (self.selectRows.length == 0) {
          self.$baseMessage('请选择一条记录', 'error')
          return
        }
        if (self.selectRows.length > 1) {
          self.$baseMessage('只能选择一条记录', 'error')
          return
        }
        self
          .$prompt('请输入数据库密码', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
          })
          .then(({ value }) => {
            setStorageValue('db_password_' + self.selectRows[0].name, value)
          })
          .catch((err) => {})
      },
    },
  }
</script>
<style></style>
