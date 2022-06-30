<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="dialogFormVisible"
    :close-on-click-modal="false"
    width="80%"
    @close="handleClose"
  >
    <el-form ref="form" :model="form" label-width="180px">
      <el-form-item label="文件" prop="fileList">
        <el-upload
          ref="uploadfile"
          v-model="fileList"
          drag
          action="#"
          :file-list="fileList"
          :on-change="onUpload"
          :on-remove="handleRemove"
          :auto-upload="false"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            将文件拖到此处，或
            <em>点击上传</em>
          </div>
        </el-upload>
      </el-form-item>
      <el-form-item
        label="工程名(允许换名)"
        prop="workspace_name"
        :required="true"
      >
        <el-input
          v-model.trim="form.workspace_name"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="进度" prop="progress">
        <el-input
          v-model="progress"
          type="textarea"
          :rows="8"
          autocomplete="off"
        ></el-input>
      </el-form-item>
    </el-form>

    <div
      slot="footer"
      class="dialog-footer"
      style="position: relative; padding-right: 15px; text-align: right"
    >
      <el-button type="primary" @click="handleClose">关闭</el-button>
      <el-button
        :loading="loading"
        size="small"
        style="margin-left: 10px"
        type="success"
        @click="submitSync"
      >
        开始导入
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
/* eslint-disable */
  import { createRequest } from '@/api/request'
  import { exportWorkspace } from '@/utils'

  export default {
    name: 'SdpWorkspaceImport',
    props: {},
    data() {
      return {
        alertTitle: '导入时间较长，请耐心等待',
        footerTip: '正在导入中...',
        addWorkspace: createRequest('sdp_workspace', 'add'),
        addWorkspaceConfig: createRequest('sdp_workspace_config', 'add'),
        addProject: createRequest('sdp_project', 'add'),
        addTemplate: createRequest('sdp_template', 'add'),
        addSql: createRequest('sdp_sql', 'add'),
        updateWorkspace: createRequest('sdp_workspace', 'update'),
        updateWorkspaceConfig: createRequest('sdp_workspace_config', 'update'),
        updateProject: createRequest('sdp_project', 'update'),
        updateTemplate: createRequest('sdp_template', 'update'),
        updateSql: createRequest('sdp_sql', 'update'),
        show: false,
        loading: false,
        dialogVisible: false,
        title: '导入',

        form: {
          workspace_name: '',
        },
        fileList: [],
        progress: '当前进度...',

        dialogFormVisible: false,
        data: {},
      }
    },
    computed: {},
    watch: {
      fileList: function (val, oldVal) {
        let self = this
        for (let file in val) {
          file = this.fileList[file]
          var reader = new FileReader()
          reader.readAsText(file.raw)
          reader.onload = function () {
            let data = JSON.parse(this.result)
            for (let workspaceName in data) {
              self.$set(self.form, 'workspace_name', workspaceName)
              break
            }
          }
          break
        }
      },
    },
    methods: {
      onUpload(file, fileList) {
        this.fileList = fileList
      },
      handleRemove(file, fileList) {
        this.fileList = fileList
      },
      getKey(item, workspaceName, tableName, keys) {
        let key_ori = ''
        for(let key in keys) {
          key = keys[key]
          if (!item[key]) {
            if (tableName == 'sdp_workspace' && key == 'name') {
              key_ori+=key+'='+workspaceName+';'
              continue;
            } else if (tableName != 'sdp_workspace' && key == 'workspace_name') {
              key_ori+=key+'='+workspaceName+';'
              continue;
            } else if (tableName == 'sdp_sql' && key == 'name' && item.parameter_catalog == 'sql') {
              key_ori+=key+'='+';'
              continue;
            } else if (tableName == 'sdp_template' && key == 'name' && item.name === '') {
              key_ori+=key+'='+';'
              continue;
            } else {
              console.error('miss key:'+key+','+tableName,item)
              key_ori+=key+'='+';'
              continue;
            }
          }
          key_ori+=key+'='+item[key]+';'
        }
        return key_ori
      },
      submitSync() {
        let self = this
        if (this.fileList.length < 1) {
          this.$baseMessage('请添加文件', 'error')
          return
        }
        if (!this.form.workspace_name) {
          this.$baseMessage('请输入工程名', 'error')
          return
        }


        let workspaceName_dest = this.form.workspace_name
        exportWorkspace(workspaceName_dest, 'with_id')
          .then((datas_ori) => {
            for (let file in this.fileList) {
              file = self.fileList[file]
              var reader = new FileReader()
              reader.readAsText(file.raw)
              reader.onload = function () {
                self.progress = '正在分析:' + file.name
                self.loading = true
                self.count = 0

                self.last_error_data = undefined

                let datas = JSON.parse(this.result)
                self.list = []
                for (let workspaceName in datas) {
                  let data = [
                    { addWorkspace: { table: 'sdp_workspace', keys: 'name' }},
                    { addWorkspaceConfig: { table: 'sdp_workspace_config', keys: 'workspace_name,name'}},
                    { addProject: { table: 'sdp_project', keys: 'workspace_name,name' }},
                    { addTemplate: { table: 'sdp_template', keys: 'workspace_name, project_name,name,file_type,project,package_name' }},
                    { addSql: { table: 'sdp_sql', keys: 'workspace_name,table_name, parameter_catalog,parameter_catalog_type,name' }},
                  ]
                  for (let dataItem in data) {
                    dataItem = data[dataItem]
                    for (let method in dataItem) {
                      let tableName = dataItem[method].table
                      let keys = dataItem[method].keys.split(',').map( item => item.trim())
                      let dataList = datas[workspaceName][tableName]
                      let dataList_ori_map = {}
                      if (datas_ori && datas_ori[tableName]) {
                        let dataList_ori = datas_ori[tableName]
                        for (let item in dataList_ori) {
                          item = {
                            _method: method,
                            _table_name: tableName,
                            _count: dataList.length,
                            ...dataList_ori[item],
                          }
                          let key_ori = self.getKey(item, workspaceName_dest, tableName, keys)
                          if (key_ori) {
                            dataList_ori_map[key_ori] = item
                          }
                        }
                      }
                      for (let item in dataList) {
                        item = {
                          _method: method,
                          _table_name: tableName,
                          _count: dataList.length,
                          ...dataList[item],
                        }
                        let key = self.getKey(item, workspaceName_dest, tableName, keys)
                        let item_ori = ''
                        if (key) {
                          item_ori = dataList_ori_map[key]
                          item.sdp_key = key
                        }
                        delete item.id
                        if (item_ori) {
                          item.id = item_ori.id
                          item._method = method.replace('add', 'update')
                          if (tableName == 'sdp_project' && item_ori.root_path && item_ori.root_path.indexOf('(root)') != 0) {
                            delete item.root_path
                          } else if (tableName == 'sdp_workspace') {
                            delete item.db_password
                          }
                        }
                        if ('addSql' == method) {
                          item = { ...item, disable_auto_param: 1 }
                        }
                        if ('addWorkspace' == method) {
                          item = { ...item, name: self.form.workspace_name }
                        } else {
                          item = {
                            ...item,
                            workspace_name: self.form.workspace_name,
                          }
                        }
                        let itemNamesObject = {
                          addTemplate: ['file_template', 'remark'],
                          addSql: ['parameter_sql', 'java_imports', 'remarks'],
                        }
                        if (method in itemNamesObject) {
                          let itemNames = itemNamesObject[method]
                          itemNames.forEach((itemName) => {
                            try {
                              let lines = item[itemName]
                              if (lines) {
                                let destLines = ''
                                lines.forEach((line) => {
                                  destLines += line + '\n'
                                })
                                item[itemName] = destLines
                              }
                            } catch (ex) {}
                          })
                        }
                        itemNamesObject = {
                          addTemplate: ['extra_info'],
                          addSql: ['extra_info'],
                        }
                        if (method in itemNamesObject) {
                          let itemNames = itemNamesObject[method]
                          itemNames.forEach((itemName) => {
                            try {
                              if (item[itemName]) {
                                item[itemName] = JSON.stringify(item[itemName], null, 2)
                                // console.log('extra_info', item[itemName], itemName, item)
                              }
                            } catch (ex) {}
                          })
                        }
                        self.list.push(item)
                      }
                    }
                  }
                  break
                }
                self.list_index = 0
                self.fire()
              }
              break
            }

          })

      },
      fire() {
        let self = this
        if (self.list_index == self.list.length) {
          self.progress += '\n导入完成'
          self.$emit('fetch-data')
          self.loading = false
          return
        }
        let item = self.list[self.list_index]
        let method = item['_method']
        let tableName = item['_table_name']
        let count = item['_count']
        let msg = '正在导入' + tableName + ',共' + count + '条...'
        if (self.progress.indexOf(msg) < 0) {
          self.progress += '\n' + msg
        }
        delete item['_method']
        delete item['_table_name']
        delete item['_count']
        self[method](item)
          .then((data) => {
            self.list_index++
            setTimeout(self.fire, 10)
          })
          .catch((err) => {
            console.log('err', err)
            console.log('item', item)
            self.loading = false
            self.progress += '\n错误：' + JSON.stringify(err)
            self.progress += '\n方法：' + method
            self.progress += '\n数据：' + JSON.stringify(item)
            self.$emit('fetch-data')
          })
      },
      reset() {
        this.title = '准备导入'
        this.progress = ''
        this.fileList = []
        this.loading = false
        this.footerTip = '正在导入中...'
      },
      handleShow(data, parentObject) {
        let alertTitle = '导入时间较长，请耐心等待'

        this.reset()
        this.form = Object.assign({}, {})
        this.dialogFormVisible = true
      },
      handleClose() {
        this.fileList = []
        this.dialogFormVisible = false
      },
    },
  }
</script>
