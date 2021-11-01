<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="dialogFormVisible"
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
      <div v-for="(item, index) in formItems" :key="index">
        <el-form-item :label="item.key">
          <el-input
            v-if="item.is_area"
            v-model="item.value"
            type="textarea"
            :rows="3"
            autocomplete="off"
          ></el-input>
          <el-input v-else v-model="item.value" autocomplete="off"></el-input>
        </el-form-item>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
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
    },
    data() {
      return {
        doAdd: createRequest(this.module, 'add'),
        doEdit: createRequest(this.module, 'update'),
        form: { items: [] },
        form_ori: {},
        formItems: [],
        rules: {},
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    methods: {
      getLabel(prop) {
        let item = this.columns.find((ele) => ele.prop === prop)
        if (!item) {
          return prop
        }
        return item.label + '(' + item.prop + ')'
      },
      showEdit(row) {
        this.title = '显示详情'
        this.form = Object.assign({}, row)
        this.form_ori = Object.assign({}, row)
        this.form.items = []
        this.formItems = []
        let obj = JSON.parse(row.content)
        for (let p in obj) {
          let o = {}
          o.key = p
          o.value = obj[p]
          if (
            p == 'parameterSql' ||
            p == 'extra_info' ||
            p == 'remark' ||
            p == 'tables' ||
            p == 'java_imports' ||
            p == 'fileTemplate'
          ) {
            o.is_area = true
          }
          this.formItems.push(o)
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.dialogFormVisible = false
      },
    },
  }
</script>
