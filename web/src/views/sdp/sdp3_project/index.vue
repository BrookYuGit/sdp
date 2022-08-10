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
              v-model="queryForm.workspace_name"
              placeholder="工作区"
              filterable
              style="width: 100%"
              :clearable="true"
              @change="onWorkspaceChange"
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
          <!-- <el-button type="primary" @click="handleShow()">
            导入
          </el-button> -->
          <!-- <el-button type="primary" @click="handleShowDownload">
            导出
          </el-button> -->
          <el-button
            type="primary"
            :loading="executeLoading"
            @click="handleExecute"
          >
            执行
          </el-button>
          <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
            添加
          </el-button>
          <el-button type="primary" @click="handleEdit">修改</el-button>
          <el-button type="primary" @click="handleBatchEdit">
            批量修改
          </el-button>
          <el-button type="primary" @click="handleUpdateRootPath">
            设置(root)目录
          </el-button>
          <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
            删除
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
          :page-sizes="[5, 10, 50, 100, 200, 500, 1000, 2000, 5000, 10000]"
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
      :workspace-list="workspaceList"
      :shop-list="shopList"
      @fetch-data="fetchWithHistoryData"
    ></table-edit>
    <table-batch-edit
      ref="batchEdit"
      :module="module"
      :columns="columns"
      :workspace-list="workspaceList"
      @fetch-data-by-update="fetchWithHistoryDataByUpdate"
      @fetch-data="fetchWithHistoryData"
    ></table-batch-edit>
  </el-row>
</template>

<script>
/* eslint-disable */
  import { createRequest } from '@/api/request'

  import { fixBlankFileds } from '@/utils'

  import TableEdit from './components/Edit'
  import TableBatchEdit from './components/BatchEdit'

  import base from '@/utils/base'

  export default {
    name: 'SdpProject',
    components: {
      TableBatchEdit,
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
      let module = 'sdp_project'
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
        doExecute: createRequest('sdp_project', 'execute'),
        getProjectList: createRequest('sdp_project', 'list'),
        getWorkspaceList: createRequest('sdp_workspace', 'list'),
        updateProject: createRequest('sdp_project', 'update'),
        without_history: true,
        window: window,
        useRsa: window.use_rsa,
        tableHeight: 550,
        imgShow: true,
        executeLoading: false,
        deptFilters: [],
        orgFilters: [],
        orgList: [],
        shopList: [],
        stationList: [],
        companyList: [],
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
          workspace_name: null,
        },

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
          prop: 'workspace_name',
          label: '工作区名称',
          width: 200,
        },
        {
          prop: 'name',
          label: '名称',
          width: 150,
        },
        {
          prop: 'tables',
          label: '表',
          width: 300,
        },
        {
          prop: 'root_path',
          label: '路径',
          width: 400,
        },
        // {
        //   prop: 'db_port',
        //   label: '数据库端口',
        // },
        // {
        //   prop: 'db_database',
        //   label: '数据库',
        //   width: 150,
        // },
        // {
        //   prop: 'db_username',
        //   label: '数据库用户名',
        // },
        // {
        //   prop: 'db_password',
        //   label: '数据库密码',
        // },
        {
          prop: 'remark',
          label: '备注',
          width: 400,
        },
        {
          prop: 'extra_info',
          label: '附加信息',
          width: 300,
        },
      ]

      this.initBase('fetchAllData')
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      cellStyle(a, b, c, d) {
        return {
          'white-space': 'pre-wrap',
          'max-height': 'fit-content',
        }
      },
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
      onWorkspaceChange(a) {
        this.initBase('onWorkspaceChange', a)
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
      fetchWithHistoryDataByUpdate() {
        this.initBase('fetchWithHistoryDataByUpdate')
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
        this.initBase(
          'handleAdd',
          this.queryForm.workspace_name
            ? { workspace_name: this.queryForm.workspace_name }
            : {}
        )
      },
      handleEdit(row) {
        this.initBase('handleEdit', row)
      },
      handleBatchEdit(row) {
        this.initBase('handleBatchEdit', row)
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
      handleUpdateRootPath() {
        this.initBase('handleUpdateRootPath')
      },
      fetchAllData() {
        this.listLoading = true

        //获得站点列表
        this.getWorkspaceList({})
          .then((data) => {
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
          order_by: [{ workspace_name: 'asc' }, { name: 'asc' }],
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
            this.finishFetchData(data)
          })
      },
      handleShow(data) {
        this.$refs['importExcel'].handleShow(data, this)
      },
      handleExecute() {
        this.initBase('handleExecute')
      },
    },
  }
</script>
<style></style>
