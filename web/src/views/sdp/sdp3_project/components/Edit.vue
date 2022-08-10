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
        <el-select v-model="form.workspace_name" filterable style="width: 100%">
          <el-option
            v-for="item in workspaceList"
            :key="item.value"
            :label="item.label"
            :value="item.label"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label="getLabel('name')" prop="name" :required="true">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('tables')" prop="tables">
        <el-input
          v-model="form.tables"
          type="textarea"
          :rows="3"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('root_path')" prop="root_path">
        <el-input v-model.trim="form.root_path" autocomplete="off"></el-input>
      </el-form-item>

      <!-- <el-form-item :label="getLabel('db_host')" prop="db_host" :required="true">
        <el-input v-model.trim="form.db_host" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('db_port')" prop="db_port" :required="true">
        <el-input v-model.trim="form.db_port" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('db_database')" prop="db_database" :required="true">
        <el-input v-model.trim="form.db_database" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('db_username')" prop="db_username" :required="true">
        <el-input v-model.trim="form.db_username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item :label="getLabel('db_password')" prop="db_password">
        <el-input v-model.trim="form.db_password" autocomplete="off"></el-input>
      </el-form-item> -->
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
    name: 'SdpProjectEdit',
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
        form: {},
        form_ori: {},
        rules: {},
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    methods: {
      asNew() {
        delete this.form.id
        this.form_ori = Object.assign({}, {})
        this.title = '添加'
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
        }
        this.dialogFormVisible = true
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
          } else {
            return false
          }
        })
      },
    },
  }
</script>
