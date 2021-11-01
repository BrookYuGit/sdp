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
      <el-form-item :label="getLabel('id')" prop="id">
        <el-input
          v-model.trim="form.id"
          autocomplete="off"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item
        :label="getLabel('workspace_name')"
        prop="workspace_name"
        :required="true"
      >
        <el-select
          v-model="form.workspace_name"
          filterable
          style="width: 100%"
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
      <el-form-item
        :label="getLabel('project_name')"
        prop="project_name"
        :required="true"
      >
        <el-select v-model="form.project_name" filterable style="width: 100%">
          <el-option
            v-for="item in projectList"
            :key="item.value"
            :label="item.label"
            :value="item.label"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label="getLabel('name')" prop="name" :required="true">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        :label="getLabel('file_type')"
        prop="file_type"
        :required="true"
      >
        <el-input v-model.trim="form.file_type" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        :label="getLabel('project')"
        prop="project"
        :required="true"
      >
        <el-input v-model.trim="form.project" autocomplete="off"></el-input>
      </el-form-item>
      <el-row>
        <el-col :xs="14" :sm="14" :md="14" :lg="14" :xl="14">
          <el-form-item
            :label="getLabel('package_name')"
            prop="package_name"
            :required="true"
          >
            <el-input
              v-model.trim="form.package_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="10" :sm="10" :md="10" :lg="10" :xl="10">
          <el-form-item
            label=""
            prop="package_name_from_config"
            style="color: blue"
          >
            <span slot="label" style="color: blue">
              &lt;= 可从配置中选择并自动填入package_name
            </span>
            <el-select
              v-model="form.package_name_from_config"
              filterable
              style="width: 100%"
              @change="onPackageNameChange"
            >
              <el-option
                v-for="item in packageNameList"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item
        :label="getLabel('file_template')"
        prop="file_template"
        :required="true"
      >
        <el-input
          v-model.trim="form.file_template"
          type="textarea"
          :rows="8"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('no_overwrite')" prop="no_overwrite">
        <el-switch
          v-model="form.no_overwrite"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#e4e7ed"
        ></el-switch>
      </el-form-item>
      <el-form-item :label="getLabel('extra_info')" prop="extra_info">
        <el-input
          v-model="form.extra_info"
          type="textarea"
          :rows="3"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('remark')" prop="remark">
        <el-input
          v-model="form.remark"
          type="textarea"
          :rows="3"
          autocomplete="off"
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
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
    name: 'SdpTemplateEdit',
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
    },
    data() {
      return {
        doAdd: createRequest(this.module, 'add'),
        doEdit: createRequest(this.module, 'update'),
        getProjectList: createRequest('sdp_project', 'list'),
        getWorkspaceConfigList: createRequest('sdp_workspace_config', 'list'),
        form: {},
        form_ori: {},
        rules: {},
        projectList: [],
        packageNameList: [],
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    methods: {
      onPackageNameChange(v) {
        this.form.package_name = '{config&name=' + v + '}'
      },
      asNew() {
        delete this.form.id
        this.form_ori = Object.assign({}, {})
        this.title = '添加'
      },
      onWorkspaceChange(v, cb) {
        this.projectList.length = 0
        if (this.form.workspace_name) {
          this.getProjectList({ workspace_name: this.form.workspace_name })
            .then(({ data }) => {
              this.projectList = data.map((ele) => {
                return { label: ele.name, value: ele.name }
              })
            })
            .then(() => {
              return this.getWorkspaceConfigList({
                workspace_name: this.form.workspace_name,
              }).then(({ data }) => {
                this.packageNameList = []
                data.map((ele) => {
                  if (ele.name.indexOf('package_') == 0) {
                    this.packageNameList.push({
                      label: ele.name,
                      value: ele.value,
                    })
                  }
                })
                this.packageNameList = this.packageNameList.sort(
                  function compareFunction(p1, p2) {
                    return p1.label.localeCompare(p2.label, 'zh')
                  }
                )
              })
            })
            .then(() => {
              if (cb) {
                cb()
              }
            })
            .catch(() => {
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
      getLabel(prop) {
        let item = this.columns.find((ele) => ele.prop === prop)
        if (!item) {
          return prop
        }
        return item.label + '(' + item.prop + ')'
      },
      showEdit(row) {
        console.log('row', row)
        if (!row || !row.id) {
          this.title = '添加'
          console.log('row', row)
          this.form_ori = Object.assign({}, {})
          this.form = Object.assign(
            {},
            {
              name: '{alias_table_name}',
              file_type: 'java',
              project: 'src/main/java',
              ...(row || {}),
            }
          )
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form_ori = Object.assign({}, row)
        }
        this.onWorkspaceChange(null, () => {
          this.dialogFormVisible = true
        })
      },
      close() {
        this.$refs['form'].resetFields()
        this.dialogFormVisible = false
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            let form = getForm(this.form, this.form_ori)
            if (!form) {
              this.$baseMessage('无改动', 'error')
              return
            }
            this.loading = true
            let func = this.doEdit
            if (this.title == '添加') {
              func = this.doAdd
            }
            func(form)
              .catch((err) => {
                this.loading = false
                this.$emit('fetch-data-by-update')
              })
              .then(({ msg }) => {
                this.loading = false
                this.$baseMessage(msg, 'success')
                this.$emit('fetch-data-by-update')
                if (this.title != '添加') {
                  this.close()
                }
              })
          } else {
            return false
          }
        })
      },
    },
  }
</script>
