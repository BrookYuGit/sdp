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
  import { createRequest } from '@/api/request'

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
                { addWorkspace: 'sdp_workspace' },
                {
                  addWorkspaceConfig: 'sdp_workspace_config',
                },
                { addProject: 'sdp_project' },
                { addTemplate: 'sdp_template' },
                { addSql: 'sdp_sql' },
              ]
              for (let dataItem in data) {
                dataItem = data[dataItem]
                for (let method in dataItem) {
                  let tableName = dataItem[method]
                  let dataList = datas[workspaceName][tableName]
                  for (let item in dataList) {
                    item = {
                      _method: method,
                      _table_name: tableName,
                      _count: dataList.length,
                      ...dataList[item],
                    }
                    delete item.id
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
                          let lines = item[itemName]
                          if (lines) {
                            item[itemName] = JSON.stringify(lines, null, 2)
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
            self.loading = false
            self.progress += '\n错误：' + JSON.stringify(err)
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
