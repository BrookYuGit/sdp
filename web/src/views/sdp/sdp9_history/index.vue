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
            <el-input
              v-model.trim="queryForm.workspace_name_like"
              placeholder="工作区"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.table_name_like"
              placeholder="分类"
              autocomplete="off"
              :clearable="true"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.content_like"
              placeholder="内容"
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
          <el-button type="primary" @click="handleEdit">显示详情</el-button>
          <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
            删除
          </el-button>
          <el-button type="danger" @click="handleClear">清空</el-button>
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
  </el-row>
</template>

<script>
  import { createRequest } from '@/api/request'

  import { fixBlankFileds } from '@/utils'

  import base from '@/utils/base'

  import TableEdit from './components/Edit'

  export default {
    name: 'SdpHistory',
    components: {
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
      let module = 'sdp_history'
      return {
        base: undefined,
        title: '项目',
        module,
        getList: createRequest(module, 'list'),
        countList: createRequest(module, 'count'),
        doDelete: createRequest(module, 'delete'),
        doEdit: createRequest(module, 'update'),
        doClear: createRequest(module, 'clear'),
        doExportExcel: createRequest(module, 'export_excel'),
        getWorkspaceList: createRequest('sdp_workspace', 'list'),
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
        {
          prop: 'table_name',
          label: '分类',
          width: 200,
        },
        {
          prop: 'update_time',
          label: '更新时间',
          width: 200,
        },
        {
          prop: 'content',
          label: '内容',
          width: 500,
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
      handleUseRsa(v) {
        this.initBase('handleUseRsa', v)
      },
      filterInputMethod({ option, row, column }) {
        return filterInputMethod(this, option, row, column)
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
        new Promise((resolve) => {
          resolve()
        })
          .then(() => {
            let data = this.getWorkspaceList({}).then((data) => {
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
            return data
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
            { update_time: 'desc' },
            { workspace_name: 'asc' },
            { table_name: 'asc' },
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
            this.finishFetchData(data)
          })
      },
      handleShow(data) {
        this.$refs['importExcel'].handleShow(data, this)
      },
      handleClear() {
        let self = this
        self.$baseConfirm('你确定要清空记录吗（无法恢复）！', null, () => {
          self.listLoading = true
          self.clear_tsp = new Date()
          self
            .doClear({})
            .catch((err) => {
              self.fetchWithHistoryData()
            })
            .then(({ data: count }) => {
              self.$baseNotify(
                '操作成功，共' +
                  count +
                  '条,用时' +
                  self.$baseDiffTsp(new Date(), self.clear_tsp),
                '清空',
                'success',
                undefined,
                24 * 60 * 60 * 1000
              )
              self.fetchWithHistoryData()
            })
        })
      },
    },
  }
</script>
<style></style>
