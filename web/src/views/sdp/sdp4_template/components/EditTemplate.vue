<template>
  <div id="cmParent">
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
        <el-row>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <span style="color: red">
              【扩展功能：转换源代码到模板注意事项】表名或字段名必须为小写字母及至少一个下划线（否则会导致&is_ajax=1和is_first_lower=1混淆）
            </span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="4">
            <el-form-item label="表名" label-width="80px" prop="table_name">
              <el-input
                v-model.trim="form.table_name"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="4">
            <el-form-item
              label="表注释"
              label-width="80px"
              prop="table_comment"
            >
              <el-input
                v-model.trim="form.table_comment"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-button type="primary" @click="translate">
              根据表名自动转换源代码到模板
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="4">
            <el-form-item
              label="字段名"
              label-width="80px"
              prop="sql_method_name"
            >
              <el-input
                v-model.trim="form.column_name"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="4">
            <el-form-item
              label="字段注释"
              label-width="80px"
              prop="column_comment"
            >
              <el-input
                v-model.trim="form.column_comment"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-button type="primary" @click="translateColumnName">
              根据字段名自动转换源代码到模板
            </el-button>
          </el-col>
        </el-row>
        <el-divider content-position="center" />
        <codemirror
          ref="cm"
          v-model="form.file_template"
          :options="cmOptions"
        ></codemirror>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
        <!-- <el-button type="primary" @click="asNew" v-if="title == '编辑'">转换为添加</el-button> -->
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { createRequest } from '@/api/request'
  import { getForm } from '@/utils'

  import { codemirror } from 'vue-codemirror'
  // require("codemirror/lib/codemirror.css");
  require('./cm.css')
  // 　require("codemirror/mode/clike/clike.js")
  window.cmSdp = {}

  window.cmSdp.commonPropKeywords = [
    'has_blobs ' + 'column_is_last ' + 'column_is_first ' + '',
  ]

  window.cmSdp.propForKeywords = [
    {
      alias_table_name:
        'is_ajax ' +
        'is_lower ' +
        'is_upper ' +
        'is_first_lower ' +
        'with_delimited ' +
        '',
    },
    {
      table_name:
        'is_ajax ' +
        'is_lower ' +
        'is_upper ' +
        'is_first_lower ' +
        'with_delimited ' +
        '',
    },
    {
      table_comment: 'is_first_line ' + '',
    },
    {
      sql_name:
        'is_ajax ' +
        'is_lower ' +
        'is_upper ' +
        'is_first_lower ' +
        'with_delimited ' +
        '',
    },
    {
      sql_param_name:
        'is_ajax ' +
        'is_lower ' +
        'is_upper ' +
        'is_first_lower ' +
        'with_delimited ' +
        '',
    },
    {
      config: 'name ' + '',
    },
    {
      value: 'value ' + '',
    },
    {
      file_name: '',
    },
    {
      file_date: '',
    },
    {
      package_name:
        'is_ajax ' +
        'is_lower ' +
        'is_upper ' +
        'is_first_lower ' +
        'with_delimited ' +
        '',
    },
    {
      serialVersionUID: '',
    },
    {
      column_name: [
        'is_ajax ' +
          'is_lower ' +
          'is_upper ' +
          'is_first_lower ' +
          'with_delimited ' +
          '',
      ],
    },
    {
      column_method_name:
        'is_ajax ' +
        'is_lower ' +
        'is_upper ' +
        'is_first_lower ' +
        'with_delimited ' +
        '',
    },
    {
      column_sql: '',
    },
    {
      column_simple_sql_with_star: '',
    },
    {
      column_simple_sql: '',
    },
    {
      column_ori_sql: '',
    },
    {
      column_comment: 'is_first_line ' + '',
    },
    {
      column_jsonformat_for_date: '',
    },
    {
      column_jsonfield_for_date: '',
    },
    {
      column_java_return_type: '',
    },
    {
      column_java_imports: '',
    },
    {
      column_java_type: 'is_full_name ' + '',
    },
    {
      column_name_for_repository: '',
    },
    {
      column_parameter_type_for_mapper: '',
    },
    {
      column_name_for_repository: '',
    },
    {
      column_param_for_repository: '',
    },
  ]

  window.cmSdp.propKeywords = ''
  window.cmSdp.propKeywordsMap = {}

  window.cmWords = function (str) {
    var obj = [],
      words = (str + '').split(' ')
    for (var i = 0; i < words.length; ++i) if (words[i]) obj.push(words[i])
    return obj
  }

  window.cmSdp.keywords = ''
  window.cmSdp.propForKeywordsMap = {}
  for (let i in window.cmSdp.propForKeywords) {
    let props = window.cmSdp.propForKeywords[i]
    for (let p in props) {
      window.cmSdp.propForKeywordsMap[p] = props[p]
      window.cmSdp.keywords += p + ' '
      let words = window.cmWords(props[p])
      for (let w in words) {
        if (words[w] in window.cmSdp.propForKeywordsMap) {
        } else {
          window.cmSdp.propForKeywordsMap[words[w]] = true
          window.cmSdp.propKeywords += words[w] + ' '
        }
      }
    }
  }

  window.cmSdp.propForBlockKeywords = [
    {
      columns: '',
    },
    {
      column: '',
    },
    {
      primary_key: '',
    },
    {
      unique_keys: '',
    },
    {
      unique_key_fields: '',
    },
    {
      table: '',
    },
    {
      sqls: '',
    },
    {
      methods: '',
    },
  ]

  window.cmSdp.blockKeywords = ''
  window.cmSdp.propForBlockKeywordsMap = {}
  for (let i in window.cmSdp.propForBlockKeywords) {
    let props = window.cmSdp.propForBlockKeywords[i]
    for (let p in props) {
      window.cmSdp.propForBlockKeywordsMap[p] = props[p]
      window.cmSdp.blockKeywords += p + ' '
    }
  }

  window.cmSdp.propKeywords += window.cmSdp.commonPropKeywords

  window.cmSdp.propKeywordsForBlock =
    'has_java_field_type_with_package ' +
    'is_primary_key ' +
    'is_primary_key_multiple ' +
    'is_only_request_columns ' +
    'sql_is_interface ' +
    //仅限于正常的{*columns}
    'is_only_request_columns ' +
    'extra_request_columns ' +
    'extra_response_columns ' +
    //仅限于{*sql}下的{*columns}
    'is_only_param_columns ' +
    'is_only_response_columns ' +
    'include_param_columns ' + //需要修改为is_
    'include_response_columns ' + //需要修改为is_
    //仅限于{*columns}
    'only_primary_key ' +
    'has_primary_key ' +
    'has_auto_increment ' +
    'has_columns_1 ' +
    'has_columns_0 ' +
    'discard ' +
    'has_columns ' +
    'column_name ' +
    'is_simple ' +
    'is_dup ' +
    'is_interface ' +
    'is_frontend_list ' +
    'sql_is_interface ' +
    'is_string ' +
    'is_blob ' +
    'is_date ' +
    'is_auto_increment ' +
    'is_import_excel ' +
    'has_column_java_imports ' +
    'param_is_nullable ' +
    'param_is_like ' +
    ''
  window.cmSdp.propKeywords += window.cmSdp.propKeywordsForBlock

  require('./clike.js')
  require('./cm.js')
  require('./idea.css')
  require('./show-hint.js')
  require('./show-hint.css')

  export default {
    name: 'SdpTemplateEdit',
    components: {
      codemirror,
    },
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
        lastContent: '',
        lastHTML: '',
        form_ori: {},
        rules: {},
        cmdEditor: undefined,
        window: window,
        cmOptions: {
          // lineNumbers:true,
          // line:true,
          // value:'',
          indentWithTabs: true,
          mode: 'text/x-java',
          theme: 'idea',
          indentUnit: 4,
          smartIndent: true,
          matchBrackets: true,
          indentWithTabs: false,
          tabSize: 4,
          extraKeys: { Ctrl: 'autocomplete' },
          hintOptions: { hint: this.handleShowHint, completeSingle: true },
          autofocus: true,
          // fixedGutter: true,
          // readOnly: false,
          // showCursorWhenSelecting: true,
          // firstLineNumber: 1,
        },
        hint: {},
        text_id: 0,
        projectList: [],
        packageNameList: [],
        workspaceConfigList: [],
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    mounted() {},
    methods: {
      uName(name, firstUpper) {
        let ret = ''
        let nextUpper = false
        for (let i = 0; i < name.length; i++) {
          let c = name.substring(i, i + 1)
          if (c == '_') {
            nextUpper = true
            continue
          }
          if ((ret == '' && firstUpper) || nextUpper) {
            ret += c.toUpperCase()
            nextUpper = false
          } else {
            ret += c
          }
        }
        return ret
      },
      ajaxName(name) {
        let ret = ''
        for (let i = 0; i < name.length; i++) {
          let c = name.substring(i, i + 1)
          if (c >= 'A' && c <= 'Z') {
            if (ret != '') {
              ret += '_'
            }
            ret += c.toLowerCase()
            continue
          }
          ret += c
        }
        // console.log('ret', ret)
        return ret
      },
      translate() {
        let tableName = this.form.table_name.trim()
        if (tableName != tableName.toLowerCase()) {
          this.$baseMessage('表名必须为小写下划线规则', 'error')
          return
        }
        if (tableName.indexOf('_') < 0) {
          this.$baseMessage('表名必须包含下划线', 'error')
          return
        }
        if (!tableName) {
          this.$baseMessage('必须输入表名', 'error')
          return
        }
        if (tableName.length < 3) {
          this.$baseMessage('表名至少为3个字符', 'error')
          return
        }
        let tableComment = this.form.table_comment.trim()
        let firstUpperName = this.uName(tableName, true)
        let firstLowerName = this.uName(tableName, false)
        let tokens = []
        tokens.push({
          name: firstLowerName,
          value: '{alias_table_name&is_first_lower=1}',
        })
        tokens.push({ name: tableName, value: '{alias_table_name&is_ajax=1}' })
        if (tableComment) {
          tokens.push({
            name: tableComment,
            value: '{table_comment&is_first_line=1}',
          })
        }
        tokens.push({ name: firstUpperName, value: '{alias_table_name}' })
        for (let p in this.workspaceConfigList) {
          p = this.workspaceConfigList[p]
          tokens.push({ name: p.value, value: '{config&name=' + p.label + '}' })
        }
        tokens = tokens.sort(function compareFunction(p1, p2) {
          return p2.name.length - p1.name.length
        })
        // console.log('tokens', tokens)
        for (let token in tokens) {
          token = tokens[token]
          let loop = 0
          while (
            loop++ < 1000 &&
            this.form.file_template.indexOf(token.name) >= 0
          ) {
            this.form.file_template = this.form.file_template.replace(
              token.name,
              token.value
            )
          }
        }
        let sep = ''
        let oriLines = this.form.file_template.split('\n\r')
        if (oriLines.length > 1) {
          sep = '\n\r'
        }
        let lines = []
        for (let line in oriLines) {
          line = oriLines[line]
          let subLines = line.split('\n')
          if (subLines.length > 1) {
            if (sep == '') {
              sep = '\n'
            }
            for (let subLine in subLines) {
              subLine = subLines[subLine]
              lines.push(subLine)
            }
          } else {
            subLines = line.split('\r')
            if (sep == '' && subLines.length > 1) {
              sep = '\r'
            }
            for (let subLine in subLines) {
              subLine = subLines[subLine]
              lines.push(subLine)
            }
          }
        }
        if (sep == '') {
          sep = '\n'
        }
        oriLines = lines
        lines = []
        let doneSet = {}
        for (let line in oriLines) {
          line = oriLines[line]
          let lineTrim = line.trim()
          if (!lineTrim) {
            lines += sep
            continue
          }
          let tokens = {
            'package ': '{package_name};',
            '* @Date': '{file_date}',
            '* @Author': '{config&name=author}',
            '* @Version': '{config&name=version}',
          }
          let doneToken = false
          for (let token in tokens) {
            if (!(token in doneSet) && lineTrim.indexOf(token) == 0) {
              let subTokenIndex = token.length
              while (
                subTokenIndex + 1 < lineTrim.length &&
                (lineTrim.substring(subTokenIndex, subTokenIndex + 1) == ' ' ||
                  lineTrim.substring(subTokenIndex, subTokenIndex + 1) == ':' ||
                  lineTrim.substring(subTokenIndex, subTokenIndex + 1) == '\t')
              ) {
                subTokenIndex++
              }
              if (
                subTokenIndex == token.length &&
                token != 'package ' &&
                this.isChar(
                  lineTrim.substring(subTokenIndex, subTokenIndex + 1)
                )
              ) {
                continue
              }
              doneSet[token] = 1
              let prop = tokens[token]
              if (subTokenIndex > token.length) {
                token += lineTrim.substring(token.length, subTokenIndex)
              }
              subTokenIndex = line.indexOf(token) + token.length
              lines += line.substring(0, subTokenIndex) + prop + sep
              doneToken = 1
              break
            }
          }
          if (doneToken) {
            continue
          }
          lines += line + sep
        }
        console.log('=======================')
        console.log('doneSet', doneSet, this.packageNameList)
        this.form.file_template = lines
        this.fixCm()
      },
      translateColumnName() {
        let tableName = this.form.column_name.trim()
        let tableComment = this.form.column_comment.trim()
        tableName = this.ajaxName(tableName)
        if (tableName != tableName.toLowerCase()) {
          this.$baseMessage('字段名必须为小写下划线规则', 'error')
          return
        }
        if (tableName.indexOf('_') < 0) {
          this.$baseMessage('字段名必须包含下划线', 'error')
          return
        }
        if (!tableName) {
          this.$baseMessage('必须输入字段名', 'error')
          return
        }
        if (tableName.length < 3) {
          this.$baseMessage('字段名至少为3个字符', 'error')
          return
        }
        let firstUpperName = this.uName(tableName, true)
        let firstLowerName = this.uName(tableName, false)
        let tokens = []
        tokens.push({
          name: firstLowerName,
          value: '{column_name&is_first_lower=1}',
        })
        tokens.push({ name: tableName, value: '{column_name&is_ajax=1}' })
        if (tableComment) {
          tokens.push({
            name: tableComment,
            value: '{column_comment&is_first_line=1}',
          })
        }
        tokens.push({ name: firstUpperName, value: '{column_name}' })
        tokens = tokens.sort(function compareFunction(p1, p2) {
          return p2.name.length - p1.name.length
        })
        // console.log('tokens', tokens)
        for (let token in tokens) {
          token = tokens[token]
          let loop = 0
          while (
            loop++ < 1000 &&
            this.form.file_template.indexOf(token.name) >= 0
          ) {
            this.form.file_template = this.form.file_template.replace(
              token.name,
              token.value
            )
          }
        }
        this.fixCm()
      },
      isChar(c) {
        if (c >= 'a' && c <= 'z') {
          return true
        }
        if (c >= 'A' && c <= 'Z') {
          return true
        }
        if (c >= '0' && c <= '9') {
          return true
        }
        if (c == '_' || c == '-') {
          return true
        }
        return false
      },
      words(str) {
        var obj = [],
          words = str.split(' ')
        for (var i = 0; i < words.length; ++i) if (words[i]) obj.push(words[i])
        return obj
      },
      words2(str) {
        var obj = [],
          words = str.split(' ')
        for (var i = 0; i < words.length; ++i) {
          if (words[i].split('--').length < 3) {
            if (words[i]) obj.push('&' + words[i] + '=')
          } else {
            if (words[i]) obj.push(words[i])
          }
        }
        return obj
      },
      handleShowHint() {
        const codeMirrorInstance = this.$refs['cm'].cminstance
        const cur = codeMirrorInstance.getCursor()
        const curLine = codeMirrorInstance.getLine(cur.line)
        const end = cur.ch
        const start = end
        let list = []
        // 根据不同情况给list赋值，默认为[]，即不显示提示框。
        const cursorTwoCharactersBefore = `${curLine.charAt(
          start - 2
        )}${curLine.charAt(start - 1)}`
        const cursorOneCharactersBefore = `${curLine.charAt(start - 1)}`
        let variablePool = {}
        const variablePoolKeys = variablePool ? Object.keys(variablePool) : []
        if (cursorTwoCharactersBefore === '{*') {
          list = this.words(window.cmSdp.blockKeywords)
        } else if (cursorOneCharactersBefore === '{') {
          list = this.words(window.cmSdp.keywords)
        } else if (cursorOneCharactersBefore === '&') {
          let startPre = start
          let token = ''
          while (startPre > 0) {
            const preCursorTwoCharactersBefore = `${curLine.charAt(
              startPre - 2
            )}${curLine.charAt(startPre - 1)}`
            const preCursorOneCharactersBefore = `${curLine.charAt(
              startPre - 1
            )}`
            if (preCursorOneCharactersBefore == '{') {
              if (token in window.cmSdp.propForKeywordsMap) {
                list = this.words(
                  window.cmSdp.propForKeywordsMap[token] +
                    '--以下为通用属性-- ' +
                    window.cmSdp.commonPropKeywords
                )
                break
              }
              break
            } else if (preCursorOneCharactersBefore == '&') {
              token = ''
            } else if (preCursorTwoCharactersBefore == '{*') {
              if (token in window.cmSdp.propForBlockKeywordsMap) {
                list = this.words(
                  window.cmSdp.propKeywordsForBlock +
                    '--以下为通用属性-- ' +
                    window.cmSdp.commonPropKeywords
                )
                break
              }
              break
            } else {
              token = preCursorOneCharactersBefore + token
            }
            startPre--
          }
        } else {
          let startPre = start
          let token = ''
          while (startPre > 0) {
            const preCursorTwoCharactersBefore = `${curLine.charAt(
              startPre - 2
            )}${curLine.charAt(startPre - 1)}`
            const preCursorOneCharactersBefore = `${curLine.charAt(
              startPre - 1
            )}`
            if (preCursorOneCharactersBefore == '{') {
              if (token in window.cmSdp.propForKeywordsMap) {
                list = this.words2(
                  window.cmSdp.propForKeywordsMap[token] +
                    '--以下为通用属性-- ' +
                    window.cmSdp.commonPropKeywords
                )
                break
              }
              break
            } else if (preCursorOneCharactersBefore == '&') {
              break
            } else if (preCursorTwoCharactersBefore == '{*') {
              if (token in window.cmSdp.propForBlockKeywordsMap) {
                list = this.words2(
                  window.cmSdp.propKeywords +
                    '--以下为通用属性-- ' +
                    window.cmSdp.commonPropKeywords
                )
                break
              }
              break
            } else {
              token = preCursorOneCharactersBefore + token
            }
            startPre--
          }
        }
        let from = CodeMirror.Pos(cur.line, start)
        let to = CodeMirror.Pos(cur.line, end)
        let hint = { list: list, from: from, to: to }
        return hint
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
                this.workspaceConfigList = []
                data.map((ele) => {
                  if (ele.name.indexOf('package_') == 0) {
                    this.packageNameList.push({
                      label: ele.name,
                      value: ele.value,
                    })
                  }
                  this.workspaceConfigList.push({
                    label: ele.name,
                    value: ele.value,
                  })
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
      fixCm() {
        if (this.$refs && this.$refs.cm && this.$refs.cm.codemirror) {
          this.$refs.cm.codemirror.setValue(this.form.file_template)
          let that = this
          this.$nextTick(() => {
            that.$refs.cm.codemirror.refresh()
            setTimeout(this.fixCm2, 1000)
          })
          return
        }
        setTimeout(this.fixCm, 100)
      },
      fixCm2() {
        let vueCodemirrow = document.getElementsByClassName('CodeMirror-code')
        if (!vueCodemirrow || vueCodemirrow.length == 0) {
          setTimeout(this.fixCm, 1000)
          return
        }
        if (
          vueCodemirrow[0].innerText + '' == this.lastHTML &&
          this.form.file_template != this.lastContent
        ) {
          setTimeout(() => {
            this.$refs.cm.codemirror.refresh()
            setTimeout(this.fixCm2, 1000)
          }, 100)
          return
        }
      },
      showEdit(row) {
        if (!row) {
          this.title = '添加'
          this.form = Object.assign({}, {})
          this.form_ori = Object.assign({}, {})
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form_ori = Object.assign({}, row)
          setTimeout(this.fixCm, 100)
        }
        this.onWorkspaceChange(null, () => {
          this.dialogFormVisible = true
        })
      },
      close() {
        this.$refs['form'].resetFields()
        this.dialogFormVisible = false
        this.lastHTML =
          document.getElementsByClassName('CodeMirror-code')[0].innerText + ''
        this.lastContent = this.form.file_template
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.form.file_template = this.$refs.cm.cminstance.getValue()
            let form = getForm(this.form, this.form_ori)
            if (!form) {
              // if (this.form.file_template) {
              //   console.log(
              //     'this.form.file_template',
              //     this.form.file_template.length,
              //     this.form.file_template.substring(0, 100)
              //   )
              // }
              // if (this.form_ori.file_template) {
              //   console.log(
              //     'this.form_ori.file_template',
              //     this.form_ori.file_template.length,
              //     this.form_ori.file_template.substring(0, 100)
              //   )
              // }
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
<style lang="scss"></style>
