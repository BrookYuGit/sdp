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
            <el-input
              v-model="queryForm.project_name_like"
              placeholder="项目名称"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryForm.name_like"
              placeholder="名称"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryForm.package_name_like"
              placeholder="包"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryForm.file_template_like"
              placeholder="模板"
              autocomplete="off"
              :clearable="true"
            ></el-input>
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
          <el-button type="primary" @click="handleEditTemplate">
            编辑模板
          </el-button>
          <el-button type="primary" @click="handleEdit">修改</el-button>
          <el-button type="primary" @click="handleBatchEdit">
            批量修改
          </el-button>
          <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
            删除
          </el-button>
          <!-- <el-button type="primary" v-if="!showRight" @click="handleShowRight(true)">
            显示历史
          </el-button>
          <el-button type="primary" v-if="showRight" @click="handleShowRight(false)">
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
      :workspace-list="workspaceList"
      @fetch-data-by-update="fetchWithHistoryDataByUpdate"
      @fetch-data="fetchWithHistoryData"
    ></table-edit>
    <table-edit-template
      ref="editTemplate"
      :module="module"
      :columns="columns"
      :workspace-list="workspaceList"
      @fetch-data-by-update="fetchWithHistoryDataByUpdate"
      @fetch-data="fetchWithHistoryData"
    ></table-edit-template>
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
  import { createRequest } from '@/api/request'

  import { fixBlankFileds } from '@/utils'

  import base from '@/utils/base'

  import TableEdit from './components/Edit'
  import TableEditTemplate from './components/EditTemplate'
  import TableBatchEdit from './components/BatchEdit'

  export default {
    name: 'SdpTemplate',
    components: {
      TableBatchEdit,
      TableEdit,
      TableEditTemplate,
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
      let module = 'sdp_template'
      return {
        base: undefined,
        title: '配置',
        module,
        getList: createRequest(module, 'list'),
        countList: createRequest(module, 'count'),
        doDelete: createRequest(module, 'delete'),
        doEdit: createRequest(module, 'update'),
        doExportExcel: createRequest(module, 'export_excel'),
        doExecute: createRequest('sdp_project', 'execute'),
        getWorkspaceList: createRequest('sdp_workspace', 'list'),
        without_history: true,
        window: window,
        useRsa: window.use_rsa,
        tableHeight: 550,
        imgShow: true,
        executeLoading: false,
        workspaceList: [],
        deptFilters: [],
        orgFilters: [],
        orgList: [],
        shopList: [],
        stationList: [],
        companyList: [],
        list: [],
        listHistory: [],
        listLoading: false,
        statusList: [],
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        background: true,
        selectRows: '',
        queryForm: {
          pageNo: 1,
          pageSize: 100,
          workspace_name: null,
          project_name_like: null,
          name_like: null,
          package_name_like: null,
          file_template_like: null,
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
        //   width: 80,
        // },
        {
          prop: 'workspace_name',
          label: '工作区名称',
          width: 200,
        },
        {
          prop: 'project_name',
          label: '项目名称',
          width: 150,
        },
        // {
        //   prop: 'package_type',
        //   label: 'PKG类型',
        //   width:120,
        // },
        {
          prop: 'name',
          label: '名称',
          width: 300,
        },
        {
          prop: 'file_type',
          label: '文件类型',
        },
        {
          prop: 'project',
          label: '工程',
        },
        {
          prop: 'package_name',
          label: '包',
          width: 300,
        },
        {
          prop: 'file_template',
          label: '模板',
        },
        {
          prop: 'no_overwrite',
          label: '不覆盖生成',
        },
        {
          prop: 'extra_info',
          label: '附加参数',
        },
        {
          prop: 'remark',
          label: '备注',
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
      fetchWithCount(cb) {
        this.listLoading = true
        this.countList({ ...fixBlankFileds(this.queryForm) })
          .then(({ data: totalCount }) => {
            this.total = totalCount
            this.fetchData(cb)
          })
          .catch((err) => {
            this.listLoading = false
          })
      },
      onTableBodyScroll(v) {
        this.scrollLeft = v.scrollLeft
        this.scrollTop = v.scrollTop
      },
      fetchData(cb) {
        this.listLoading = true
        this.selectRows = []

        this.queryForm.query_options = {
          ...(this.queryForm.query_options || {}),
          order_by: [
            { workspace_name: 'asc' },
            { project_name: 'asc' },
            { project: 'asc' },
            { package_name: 'asc' },
            { name: 'asc' },
            { file_type: 'asc' },
          ],
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
            this.finishFetchData(data, cb)
          })
      },
      handleShow(data) {
        this.$refs['importExcel'].handleShow(data, this)
      },
      handleEditTemplate(row) {
        let self = this
        if (self.selectRows.length == 0) {
          self.$baseMessage('请选择一条记录', 'error')
          return
        }
        if (self.selectRows.length > 1) {
          self.$baseMessage('只能选择一条记录', 'error')
          return
        }
        self.$refs['editTemplate'].showEdit(self.selectRows[0])
      },
      handleExecute() {
        this.initBase('handleExecute')
      },
    },
  }
</script>
<style></style>
