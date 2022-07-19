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
          <!-- <el-form-item>
            <el-input v-model="queryForm.workspace_name" placeholder="工作区" autocomplete="off" :clearable="true"></el-input>
          </el-form-item> -->
          <el-form-item>
            <el-input
              v-model="queryForm.table_name_like"
              placeholder="表"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryForm.parameter_catalog_like"
              placeholder="分类"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryForm.parameter_catalog_type_like"
              placeholder="分类详细类型"
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
              v-model="queryForm.parameter_sql_like"
              placeholder="SQL"
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
          <el-button type="primary" @click="handleClone">克隆</el-button>
          <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
            添加
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
      :parameter-catalog-list="parameterCatalogList"
      @fetch-data="fetchWithHistoryData"
    ></table-edit>
    <table-batch-edit
      ref="batchEdit"
      :module="module"
      :columns="columns"
      :workspace-list="workspaceList"
      :parameter-catalog-list="parameterCatalogList"
      @fetch-data-by-update="fetchWithHistoryDataByUpdate"
      @fetch-data="fetchWithHistoryData"
    ></table-batch-edit>
  </el-row>
</template>

<script>
/* eslint-disable */
  import { createRequest } from '@/api/request'

  import { fixBlankFileds } from '@/utils'

  import base from '@/utils/base'

  import TableEdit from './components/Edit'
  import TableBatchEdit from './components/BatchEdit'

  import dayjs from 'dayjs'

  export default {
    name: 'SdpSql',
    components: {
      TableEdit,
      TableBatchEdit,
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
      let module = 'sdp_sql'
      return {
        base: undefined,
        title: '配置文件',
        module,
        getList: createRequest(module, 'list'),
        countList: createRequest(module, 'count'),
        doDelete: createRequest(module, 'delete'),
        doEdit: createRequest(module, 'update'),
        doExportExcel: createRequest(module, 'export_excel'),
        doClone: createRequest(module, 'clone'),
        doExecute: createRequest('sdp_project', 'execute'),
        getWorkspaceList: createRequest('sdp_workspace', 'list'),
        without_history: true,
        window: window,
        useRsa: window.use_rsa,
        tableHeight: 550,
        imgShow: true,
        executeLoading: false,
        workspaceList: [],
        parameterCatalogList: [
          { label: 'SQL', value: 'sql' },
          { label: 'SQL参数', value: 'sql.param' },
          { label: 'SQL结果属性（扩展）', value: 'sql.response' },
          { label: '自定义方法', value: 'api.facade' },
          { label: '内置方法请求属性（扩展request）', value: 'api.request' },
          { label: '内置方法结果属性（扩展response）', value: 'api.response' },
        ],
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
          table_name_like: null,
          parameter_catalog_like: null,
          parameter_catalog_type_like: null,
          name_like: null,
          sql_like: null,
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
          prop: 'workspace_name',
          label: '工作区名称',
          width: 200,
        },
        // {
        //   prop: 'project_name',
        //   label: '项目名称',
        //   width:120,
        // },
        {
          prop: 'table_name',
          label: '表',
          width: 250,
        },
        {
          prop: 'parameter_catalog_display_name',
          label: '分类',
        },
        {
          prop: 'parameter_catalog_type',
          label: '方法名',
          width: 250,
        },
        {
          prop: 'name',
          label: '字段名称',
          width: 150,
        },
        {
          prop: 'java_type',
          label: '字段java类型',
          width: 200,
        },
        {
          prop: 'remarks',
          label: '字段备注',
          width: 120,
        },
        {
          prop: 'parameter_is_like',
          label: '字段是否为LIKE',
        },
        {
          prop: 'parameter_sql_value',
          label: '字段在SQL中的表达式值',
          width: 150,
        },
        {
          prop: 'parameter_without_test',
          label: '字段是否不在XML中生成test',
        },
        {
          prop: 'parameter_sql',
          label: 'SQL',
        },
        {
          prop: 'parameter_nullable',
          label: '字段是否允许为空',
        },
        {
          prop: 'parameter_sql_value_ignore',
          label: '是否不在SQL中查找表达式值',
        },
        // {
        //   prop: 'parameter_sql_issimple',
        //   label: '是否在XML中不生成Example',
        // },
        {
          prop: 'java_imports',
          label: 'java引入类',
        },
        {
          prop: 'java_return_type',
          label: '字段java返回类型',
        },
        {
          prop: 'is_interface',
          label: '是否为接口',
        },
        {
          prop: 'extra_info',
          label: '扩展属性',
        },
        {
          prop: 'is_nolist',
          label: '扩展属性-是否不返回列表类型',
        },
        {
          prop: 'is_frontend_list',
          label: '扩展属性-是否为前端列表页',
        },
        {
          prop: 'url',
          label: '扩展属性-url',
        },
        {
          prop: 'response_default_value',
          label: '扩展属性-response_default_value',
        },
        {
          prop: 'request_json_name',
          label: '扩展属性-request_json_name',
        },
        {
          prop: 'response_json_name',
          label: '扩展属性-response_json_name',
        },
        {
          prop: 'sort_no',
          label: '排序值',
        },
        {
          prop: 'parameter_mode',
          label: '方式',
          width: 120,
        },
        {
          prop: 'parameter_is_import_excel',
          label: '字段是否为导入Excel方法',
        },
        {
          prop: 'parameter_is_export_excel',
          label: '字段是否为导出Excel方法',
        },
        {
          prop: 'parameter_sql_value_item',
          label: 'parameter_sql_value_item',
        },
        {
          prop: 'parameter_sql_return_nolist',
          label: '返回类型为DAO类型',
        },
        {
          prop: 'parameter_overwrite_default_sql',
          label: '是否覆盖缺省的list方法',
        },
      ]

      this.initBase('fetchAllData')

      this.execute_tsp = new Date()
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
      handleExecute() {
        this.initBase('handleExecute')
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
          order_by: [
            { workspace_name: 'asc' },
            { table_name: 'asc' },
            { parameter_catalog_type: 'asc' },
            { parameter_catalog: 'asc' },
            { name: 'asc' },
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
            data.map((item) => {
              item.parameter_catalog_display_name = (
                this.parameterCatalogList.find(
                  (ele) => ele.value === item.parameter_catalog
                ) || {}
              ).label
              if (item.extra_info) {
                try {
                  const extra_info = JSON.parse(item.extra_info)
                  for (const p in extra_info) {
                    const column = this.columns.find((c) => c.prop == p)
                    if (!column) {
                      this.columns.push({ prop: p, label: p })
                    }
                    item[p] = extra_info[p]
                  }
                } catch (ex) {
                  console.error('ex', ex)
                  console.error('extra_info', item.extra_info)
                  console.error('item', item)
                }
              }
            })
            this.finishFetchData(data)
          })
      },
      handleShow(data) {
        this.$refs['importExcel'].handleShow(data, this)
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
    },
  }
</script>
<style></style>
