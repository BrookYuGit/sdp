<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="dialogFormVisible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    top="0px"
    width="80%"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="300px">
      <!-- <el-form-item :label="getLabel('id')" prop="id">
        <el-input
          v-model.trim="form.id"
          autocomplete="off"
          :disabled="true"
        ></el-input>
      </el-form-item> -->
      <el-form-item :label="getLabel('workspace_name')" prop="workspace_name">
        <el-select
          v-model="form.workspace_name"
          filterable
          style="width: 100%"
          @change="onProjectChange"
        >
          <el-option
            v-for="item in workspaceList"
            :key="item.value"
            :label="item.label"
            :value="item.label"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        :label="getLabel('table_name')"
        prop="table_name"
        :required="true"
      >
        <el-input
          v-model.trim="form.table_name"
          autocomplete="off"
          :disabled="true"
        ></el-input>
        <el-select
          v-model="form.table_name"
          filterable
          :loading="tableLoading"
          style="width: 100%"
        >
          <el-option
            v-for="item in tableList"
            :key="item.value"
            :label="item.label"
            :value="item.label"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        :label="getLabel('parameter_catalog_display_name')"
        prop="parameter_catalog"
      >
        <el-select
          v-model="form.parameter_catalog"
          filterable
          style="width: 100%"
        >
          <el-option
            v-for="item in parameterCatalogList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        :label="getLabel('parameter_catalog_type')"
        prop="parameter_catalog_type"
      >
        <el-input
          v-model.trim="form.parameter_catalog_type"
          autocomplete="off"
          @change="nameChanged"
        ></el-input>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'name')"
        :label="getLabel('name')"
        prop="name"
      >
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'java_type')"
        :label="getLabel('java_type')"
        prop="java_type"
      >
        <el-input v-model.trim="form.java_type" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'parameter_is_like')"
        :label="getLabel('parameter_is_like')"
        prop="parameter_is_like"
      >
        <el-switch
          v-model="form.parameter_is_like"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'parameter_sql_value')"
        :label="getLabel('parameter_sql_value')"
        prop="parameter_sql_value"
      >
        <el-input
          v-model.trim="form.parameter_sql_value"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item
        v-if="
          dataChangeCount > 0 &&
          testDisabled(form, 'parameter_sql_value_ignore')
        "
        :label="getLabel('parameter_sql_value_ignore')"
        prop="parameter_sql_value_ignore"
      >
        <el-switch
          v-model="form.parameter_sql_value_ignore"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="
          dataChangeCount > 0 && testDisabled(form, 'parameter_without_test')
        "
        :label="getLabel('parameter_without_test')"
        prop="parameter_without_test"
      >
        <el-switch
          v-model="form.parameter_without_test"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'parameter_sql')"
        :label="getLabel('parameter_sql')"
        prop="parameter_sql"
      >
        <font color="red">
          *）需要将通配符*扩展的查询，被查询的表必须有别名，例如：select t.*
          from table1 t，否则星号无法被自动扩展为字段名，例如select * from
          table1是错误的写法
        </font>
        <br />
        <font color="red">*）自动处理的参数必须独立一行，且以“and ”开始</font>
        <br />
        <font color="red">
          *）自动处理的参数字段名必须在左，值在右，例如t.a = 1，不可写为1 = t.a
        </font>
        <el-input
          v-model="form.parameter_sql"
          type="textarea"
          :rows="7"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item
        v-if="
          dataChangeCount > 0 && testDisabled(form, 'parameter_sql_issimple')
        "
        :label="getLabel('parameter_sql_issimple')"
        prop="parameter_sql_issimple"
      >
        <el-switch
          v-model="form.parameter_sql_issimple"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'is_interface')"
        :label="getLabel('is_interface')"
        prop="is_interface"
      >
        <el-switch
          v-model="form.is_interface"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'is_frontend_list')"
        :label="getLabel('is_frontend_list')"
        prop="is_frontend_list"
      >
        <el-switch
          v-model="form.is_frontend_list"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'parameter_nullable')"
        :label="getLabel('parameter_nullable')"
        prop="parameter_nullable"
      >
        <el-switch
          v-model="form.parameter_nullable"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'java_imports')"
        :label="getLabel('java_imports')"
        prop="java_imports"
      >
        <el-input
          v-model="form.java_imports"
          type="textarea"
          :rows="3"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item
        v-if="dataChangeCount > 0 && testDisabled(form, 'java_return_type')"
        :label="getLabel('java_return_type')"
        prop="java_return_type"
      >
        <el-input
          v-model.trim="form.java_return_type"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('remarks')" prop="remarks">
        <el-input v-model.trim="form.remarks" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('sort_no')" prop="sort_no">
        <el-input v-model.trim="form.sort_no" autocomplete="off"></el-input>
      </el-form-item>
      <!-- <el-form-item :label="getLabel('parameter_mode')" prop="parameter_mode">
        <el-input v-model.trim="form.parameter_mode" autocomplete="off"></el-input>
      </el-form-item> -->
      <el-form-item
        v-if="
          dataChangeCount > 0 && testDisabled(form, 'parameter_is_import_excel')
        "
        :label="getLabel('parameter_is_import_excel')"
        prop="parameter_is_import_excel"
      >
        <el-switch
          v-model="form.parameter_is_import_excel"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="
          dataChangeCount > 0 && testDisabled(form, 'parameter_is_export_excel')
        "
        :label="getLabel('parameter_is_export_excel')"
        prop="parameter_is_export_excel"
      >
        <el-switch
          v-model="form.parameter_is_export_excel"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <!-- <el-form-item :label="getLabel('parameter_sql_value_item')" prop="parameter_sql_value_item">
        <el-input v-model.trim="form.parameter_sql_value_item" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('parameter_sql_return_nolist')" prop="parameter_sql_return_nolist">
        <el-switch v-model="form.parameter_sql_return_nolist"
          :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#e4e7ed">
        </el-switch>
      </el-form-item>
      <el-form-item :label="getLabel('parameter_overwrite_default_sql')" prop="parameter_overwrite_default_sql">
        <el-switch v-model="form.parameter_overwrite_default_sql"
          :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#e4e7ed">
        </el-switch>
      </el-form-item> -->
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">
        确 定
      </el-button>
      <el-button v-if="title == '编辑'" type="primary" @click="asNew">
        转换为添加
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { createRequest } from '@/api/request'
  import { getForm } from '@/utils'

  export default {
    name: 'DailyReportStaffEdit',
    props: {
      module,
      columns: {
        type: Array,
        default: () => {
          return []
        },
        required: true,
      },
      workspaceList: {
        type: Array,
        default: () => {
          return []
        },
        required: true,
      },
      parameterCatalogList: {
        type: Array,
        default: () => {
          return []
        },
        required: true,
      },
    },
    data() {
      return {
        doAdd: createRequest(this.module, 'add'),
        doEdit: createRequest(this.module, 'update'),
        getWorkspaceList: createRequest('sdp_workspace', 'list'),
        getTableList: createRequest('sdp_workspace', 'get_table_list'),
        form: {},
        form_ori: {},
        rules: {},
        name_changed: undefined,
        dataChangeCount: 1,
        tableLoading: false,
        tableList: [],
        loading: false,
        title: '',
        dialogFormVisible: false,
      }
    },
    watch: {
      form: function (val, oldVal) {
        this.dataChangeCount++
      },
    },
    created() {},
    methods: {
      nameChanged(v) {
        let self = this
        if (
          v &&
          this.title == '编辑' &&
          v != this.form_ori.parameter_catalog_type
        ) {
          self.$baseConfirm(
            '您修改了名称，是否需要转为为添加，而不是直接编辑？',
            null,
            () => {
              self.name_changed = false
              self.asNew()
            }
          )
        }
      },
      asNew() {
        delete this.form.id
        this.form_ori = Object.assign({}, {})
        this.title = '添加'
      },
      onProjectChange(v, cb) {
        this.tableList.length = 0
        if (this.form.workspace_name) {
          this.tableLoading = true
          this.getTableList({
            workspace_name: this.form.workspace_name,
            name: this.form.workspace_name,
          })
            .then(({ data }) => {
              this.tableLoading = false
              this.tableList = data.map((ele) => {
                return { label: ele.name, value: ele.name }
              })
              if (cb) {
                cb()
              }
            })
            .catch(() => {
              this.tableLoading = false
              if (cb) {
                cb()
              }
            })
        } else {
          if (cb) {
            cb()
          }
        }
      },
      testDisabled(form, p) {
        if (form.parameter_catalog == 'sql') {
          if (
            [
              'parameter_sql',
              'is_interface',
              'is_frontend_list',
              // 'parameter_sql_issimple',
            ].indexOf(p) >= 0
          ) {
            return true
          }
          return false
        }
        if (form.parameter_catalog == 'sql.param') {
          if (
            [
              'name',
              'java_type',
              'parameter_nullable',
              'parameter_is_like',
              'parameter_sql_value',
              'parameter_sql_value_ignore',
              'parameter_without_test',
              'java_imports',
            ].indexOf(p) >= 0
          ) {
            return true
          }
          return false
        }
        if (form.parameter_catalog == 'api.facade') {
          if (
            [
              'name',
              'java_type',
              'java_imports',
              'parameter_is_import_excel',
              'parameter_is_export_excel',
              'java_return_type',
            ].indexOf(p) >= 0
          ) {
            return true
          }
          return false
        }
        if (
          form.parameter_catalog == 'api.request' ||
          form.parameter_catalog == 'api.response' ||
          form.parameter_catalog == 'sql.response'
        ) {
          if (
            ['name', 'java_type', 'java_imports', 'parameter_nullable'].indexOf(
              p
            ) >= 0
          ) {
            return true
          }
          return false
        }
        return false
      },
      getLabel(prop) {
        let item = this.columns.find((ele) => ele.prop === prop)
        if (!item) {
          return prop
        }
        return item.label + '(' + item.prop + ')'
      },
      showEdit(row) {
        if (!row || !row.id) {
          this.title = '添加'
          this.form = Object.assign({}, row || {})
          this.form_ori = Object.assign({}, {})
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form_ori = Object.assign({}, row)
          this.name_changed = undefined
        }
        this.onProjectChange(null, () => {
          this.dialogFormVisible = true
        })
      },
      close() {
        this.$refs['form'].resetFields()
        this.dialogFormVisible = false
      },
      doSave(func, form) {
        this.loading = true
        func(form)
          .catch((err) => {
            this.loading = false
            this.$emit('fetch-data')
          })
          .then(({ msg }) => {
            this.loading = false
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')
            if (this.title != '添加') {
              this.close()
            }
          })
      },
      save() {
        let self = this
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            let form = getForm(this.form, this.form_ori)
            if (!form) {
              this.$baseMessage('无改动', 'error')
              return
            }
            let extra_info
            if (this.form_ori.extra_info) {
              extra_info = JSON.parse(this.form_ori.extra_info)
            } else {
              extra_info = {}
            }
            if ('is_frontend_list' in form) {
              extra_info.is_frontend_list = form.is_frontend_list
            }
            form.extra_info = JSON.stringify(extra_info)
            console.log('form', form)
            let func = this.doEdit
            if (this.title == '添加') {
              func = this.doAdd
            } else {
              if (
                form.parameter_catalog_type &&
                this.name_changed === undefined
              ) {
                self.$baseConfirm(
                  '您修改了名称，是否需要转为为添加，而不是直接编辑？',
                  null,
                  () => {
                    self.name_changed = false
                    self.asNew()
                  },
                  () => {
                    self.doSave(func, form)
                  }
                )
                return false
              }
            }
            self.doSave(func, form)
          } else {
            return false
          }
        })
      },
    },
  }
</script>
