/* eslint-disable */
import { storage } from '@/config'
import { createRequest } from '@/api/request'

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 格式化时间
 * @param time
 * @param cFormat
 * @returns {string|null}
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (typeof time === 'string' && /^[0-9]+$/.test(time)) {
      time = parseInt(time)
    }
    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay(),
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 格式化时间
 * @param time
 * @param option
 * @returns {string}
 */
export function formatTime(time, option) {
  if (('' + time).length === 10) {
    time = parseInt(time) * 1000
  } else {
    time = +time
  }
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 将url请求参数转为json格式
 * @param url
 * @returns {{}|any}
 */
export function paramObj(url) {
  const search = url.split('?')[1]
  if (!search) {
    return {}
  }
  return JSON.parse(
    '{"' +
      decodeURIComponent(search)
        .replace(/"/g, '\\"')
        .replace(/&/g, '","')
        .replace(/=/g, '":"')
        .replace(/\+/g, ' ') +
      '"}'
  )
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 父子关系的数组转换成树形结构数据
 * @param data
 * @returns {*}
 */
export function translateDataToTree(data) {
  const parent = data.filter(
    (value) => value.parentId === 'undefined' || value.parentId == null
  )
  const children = data.filter(
    (value) => value.parentId !== 'undefined' && value.parentId != null
  )
  const translator = (parent, children) => {
    parent.forEach((parent) => {
      children.forEach((current, index) => {
        if (current.parentId === parent.id) {
          const temp = JSON.parse(JSON.stringify(children))
          temp.splice(index, 1)
          translator([current], temp)
          typeof parent.children !== 'undefined'
            ? parent.children.push(current)
            : (parent.children = [current])
        }
      })
    })
  }
  translator(parent, children)
  return parent
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 树形结构数据转换成父子关系的数组
 * @param data
 * @returns {[]}
 */
export function translateTreeToData(data) {
  const result = []
  data.forEach((item) => {
    const loop = (data) => {
      result.push({
        id: data.id,
        name: data.name,
        parentId: data.parentId,
      })
      const child = data.children
      if (child) {
        for (let i = 0; i < child.length; i++) {
          loop(child[i])
        }
      }
    }
    loop(item)
  })
  return result
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 10位时间戳转换
 * @param time
 * @returns {string}
 */
export function tenBitTimestamp(time) {
  const date = new Date(time * 1000)
  const y = date.getFullYear()
  let m = date.getMonth() + 1
  m = m < 10 ? '' + m : m
  let d = date.getDate()
  d = d < 10 ? '' + d : d
  let h = date.getHours()
  h = h < 10 ? '0' + h : h
  let minute = date.getMinutes()
  let second = date.getSeconds()
  minute = minute < 10 ? '0' + minute : minute
  second = second < 10 ? '0' + second : second
  return y + '年' + m + '月' + d + '日 ' + h + ':' + minute + ':' + second //组合
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 13位时间戳转换
 * @param time
 * @returns {string}
 */
export function thirteenBitTimestamp(time) {
  const date = new Date(time / 1)
  const y = date.getFullYear()
  let m = date.getMonth() + 1
  m = m < 10 ? '' + m : m
  let d = date.getDate()
  d = d < 10 ? '' + d : d
  let h = date.getHours()
  h = h < 10 ? '0' + h : h
  let minute = date.getMinutes()
  let second = date.getSeconds()
  minute = minute < 10 ? '0' + minute : minute
  second = second < 10 ? '0' + second : second
  return y + '年' + m + '月' + d + '日 ' + h + ':' + minute + ':' + second //组合
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 获取随机id
 * @param length
 * @returns {string}
 */
export function uuid(length = 32) {
  const num = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890'
  let str = ''
  for (let i = 0; i < length; i++) {
    str += num.charAt(Math.floor(Math.random() * num.length))
  }
  return str
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description m到n的随机数
 * @param m
 * @param n
 * @returns {number}
 */
export function random(m, n) {
  return Math.floor(Math.random() * (m - n) + n)
}

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description addEventListener
 * @type {function(...[*]=)}
 */
export const on = (function () {
  return function (element, event, handler, useCapture = false) {
    if (element && event && handler) {
      element.addEventListener(event, handler, useCapture)
    }
  }
})()

/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description removeEventListener
 * @type {function(...[*]=)}
 */
export const off = (function () {
  return function (element, event, handler, useCapture = false) {
    if (element && event) {
      element.removeEventListener(event, handler, useCapture)
    }
  }
})()

export function getForm(form, ori) {
  let data = {}
  let count = 0
  for (let p in form) {
    if (p == 'id') {
      continue
    }
    if (!(p in ori) || form[p] != ori[p]) {
      data[p] = form[p]
      count++
    }
  }
  if (count == 0) {
    return undefined
  }
  if ('id' in ori) {
    data.id = ori.id
  }
  return data
}

export function getStorageValue(key) {
  if (storage) {
    if ('localStorage' === storage) {
      return localStorage.getItem(key)
    } else if ('sessionStorage' === storage) {
      return sessionStorage.getItem(key)
    } else {
      return localStorage.getItem(key)
    }
  } else {
    return localStorage.getItem(key)
  }
}

export function setStorageValue(key, value) {
  if (storage) {
    if ('localStorage' === storage) {
      return localStorage.setItem(key, value)
    } else if ('sessionStorage' === storage) {
      return sessionStorage.setItem(key, value)
    } else {
      return localStorage.setItem(key, value)
    }
  } else {
    return localStorage.setItem(key, value)
  }
}

export function removeStorageValue(key) {
  if (storage) {
    if ('localStorage' === storage) {
      return localStorage.removeItem(key)
    } else if ('sessionStorage' === storage) {
      return sessionStorage.clear()
    } else {
      return localStorage.removeItem(key)
    }
  } else {
    return localStorage.removeItem(key)
  }
}

export function arrayBufferToString(arr) {
  if (typeof arr === 'string') {
    return arr
  }
  var dataview = new DataView(arr)
  var ints = new Uint8Array(arr.byteLength)
  for (var i = 0; i < ints.length; i++) {
    ints[i] = dataview.getUint8(i)
  }
  arr = ints
  var str = '',
    _arr = arr
  for (var i = 0; i < _arr.length; i++) {
    var one = _arr[i].toString(2),
      v = one.match(/^1+?(?=0)/)
    if (v && one.length == 8) {
      var bytesLength = v[0].length
      var store = _arr[i].toString(2).slice(7 - bytesLength)
      for (var st = 1; st < bytesLength; st++) {
        store += _arr[st + i].toString(2).slice(2)
      }
      str += String.fromCharCode(parseInt(store, 2))
      i += bytesLength - 1
    } else {
      str += String.fromCharCode(_arr[i])
    }
  }
  return str
}

export function arrayBufferIsZip(arr) {
  if (typeof arr === 'string') {
    return false
  }
  var dataview = new DataView(arr)
  var ints = new Uint8Array(arr.byteLength)
  for (var i = 0; i < ints.length; i++) {
    ints[i] = dataview.getUint8(i)
  }
  arr = ints
  var str = '',
    _arr = arr
  for (var i = 0; i < _arr.length && i < 10; i++) {
    var one = _arr[i].toString(2),
      v = one.match(/^1+?(?=0)/)
    if (v && one.length == 8) {
      var bytesLength = v[0].length
      var store = _arr[i].toString(2).slice(7 - bytesLength)
      for (var st = 1; st < bytesLength; st++) {
        store += _arr[st + i].toString(2).slice(2)
      }
      str += String.fromCharCode(parseInt(store, 2))
      i += bytesLength - 1
    } else {
      str += String.fromCharCode(_arr[i])
    }
  }
  if (str.length > 2 && str.substring(0, 2) == 'PK') {
    return true
  }
  return false
}

export function fixBlankFileds(obj) {
  for (let p in obj) {
    if (obj[p] === '') {
      delete obj[p]
    }
  }
  return obj
}

export function trimBlanks(v) {
  if (!v) {
    return ''
  }
  v = v + ''
  return v
    .replace(/ /g, '')
    .replace(/　/g, '')
    .replace(/\n/g, '')
    .replace(/\r/g, '')
    .replace(/\t/g, '')
}

export function getSelection() {
  var txt = ''
  if (window.getSelection) {
    txt = window.getSelection()
  } else if (document.getSelection) {
    txt = document.getSelection()
  } else if (document.selection) {
    txt = document.selection.createRange().text
  } else return ''
  return txt + ''
}

export function saveAs() {
  let view = window
  if (
    typeof view === 'undefined' ||
    (typeof navigator !== 'undefined' &&
      /MSIE [1-9]\./.test(navigator.userAgent))
  ) {
    return
  }
  var doc = view.document,
    // only get URL when necessary in case Blob.js hasn't overridden it yet
    get_URL = function () {
      return view.URL || view.webkitURL || view
    },
    save_link = doc.createElementNS('http://www.w3.org/1999/xhtml', 'a'),
    can_use_save_link = 'download' in save_link,
    click = function (node) {
      var event = new MouseEvent('click')
      node.dispatchEvent(event)
    },
    is_safari = /constructor/i.test(view.HTMLElement) || view.safari,
    is_chrome_ios = /CriOS\/[\d]+/.test(navigator.userAgent),
    throw_outside = function (ex) {
      ;(view.setImmediate || view.setTimeout)(function () {
        throw ex
      }, 0)
    },
    force_saveable_type = 'application/octet-stream',
    // the Blob API is fundamentally broken as there is no "downloadfinished" event to subscribe to
    arbitrary_revoke_timeout = 1000 * 40, // in ms
    revoke = function (file) {
      var revoker = function () {
        if (typeof file === 'string') {
          // file is an object URL
          get_URL().revokeObjectURL(file)
        } else {
          // file is a File
          file.remove()
        }
      }
      setTimeout(revoker, arbitrary_revoke_timeout)
    },
    dispatch = function (filesaver, event_types, event) {
      event_types = [].concat(event_types)
      var i = event_types.length
      while (i--) {
        var listener = filesaver['on' + event_types[i]]
        if (typeof listener === 'function') {
          try {
            listener.call(filesaver, event || filesaver)
          } catch (ex) {
            throw_outside(ex)
          }
        }
      }
    },
    auto_bom = function (blob) {
      // prepend BOM for UTF-8 XML and text/* types (including HTML)
      // note: your browser will automatically convert UTF-16 U+FEFF to EF BB BF
      if (
        /^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(
          blob.type
        )
      ) {
        return new Blob([String.fromCharCode(0xfeff), blob], {
          type: blob.type,
        })
      }
      return blob
    },
    FileSaver = function (blob, name, no_auto_bom) {
      if (!no_auto_bom) {
        blob = auto_bom(blob)
      }
      // First try a.download, then web filesystem, then object URLs
      var filesaver = this,
        type = blob.type,
        force = type === force_saveable_type,
        object_url,
        dispatch_all = function () {
          dispatch(filesaver, 'writestart progress write writeend'.split(' '))
        },
        // on any filesys errors revert to saving with object URLs
        fs_error = function () {
          if ((is_chrome_ios || (force && is_safari)) && view.FileReader) {
            // Safari doesn't allow downloading of blob urls
            var reader = new FileReader()
            reader.onloadend = function () {
              var url = is_chrome_ios
                ? reader.result
                : reader.result.replace(/^data:[^;]*;/, 'data:attachment/file;')
              var popup = view.open(url, '_blank')
              if (!popup) view.location.href = url
              url = undefined // release reference before dispatching
              filesaver.readyState = filesaver.DONE
              dispatch_all()
            }
            reader.readAsDataURL(blob)
            filesaver.readyState = filesaver.INIT
            return
          }
          // don't create more object URLs than needed
          if (!object_url) {
            object_url = get_URL().createObjectURL(blob)
          }
          if (force) {
            view.location.href = object_url
          } else {
            var opened = view.open(object_url, '_blank')
            if (!opened) {
              // Apple does not allow window.open, see https://developer.apple.com/library/safari/documentation/Tools/Conceptual/SafariExtensionGuide/WorkingwithWindowsandTabs/WorkingwithWindowsandTabs.html
              view.location.href = object_url
            }
          }
          filesaver.readyState = filesaver.DONE
          dispatch_all()
          revoke(object_url)
        }
      filesaver.readyState = filesaver.INIT

      if (can_use_save_link) {
        object_url = get_URL().createObjectURL(blob)
        setTimeout(function () {
          save_link.href = object_url
          save_link.download = name
          click(save_link)
          dispatch_all()
          revoke(object_url)
          filesaver.readyState = filesaver.DONE
        })
        return
      }

      fs_error()
    },
    FS_proto = FileSaver.prototype,
    saveAs = function (blob, name, no_auto_bom) {
      return new FileSaver(blob, name || blob.name || 'download', no_auto_bom)
    }
  // IE 10+ (native saveAs)
  if (typeof navigator !== 'undefined' && navigator.msSaveOrOpenBlob) {
    return function (blob, name, no_auto_bom) {
      name = name || blob.name || 'download'

      if (!no_auto_bom) {
        blob = auto_bom(blob)
      }
      return navigator.msSaveOrOpenBlob(blob, name)
    }
  }

  FS_proto.abort = function () {}
  FS_proto.readyState = FS_proto.INIT = 0
  FS_proto.WRITING = 1
  FS_proto.DONE = 2

  FS_proto.error =
    FS_proto.onwritestart =
    FS_proto.onprogress =
    FS_proto.onwrite =
    FS_proto.onabort =
    FS_proto.onerror =
    FS_proto.onwriteend =
      null

  return saveAs
}

export function exportWorkspace(workspaceName, with_id) {
  let datas = {}

  let self = {
    getProjectList: createRequest('sdp_project', 'list'),
    getWorkspaceList: createRequest('sdp_workspace', 'list'),
    getWorkspaceConfigList: createRequest('sdp_workspace_config', 'list'),
    getTemplateList: createRequest('sdp_template', 'list'),
    getSqlList: createRequest('sdp_sql', 'list'),
  }

  return self.getWorkspaceList({
    name: workspaceName
  })
    .then((data) => {
      datas['sdp_workspace'] = [ {...data.data[0], id: with_id ? data.data[0].id : null, name: null}]
      return self.getWorkspaceConfigList({
        workspace_name: workspaceName,
        query_options: {
          order_by: [{ workspace_name: 'asc' }, { name: 'asc' }],
        },
      })
        .then((data) => {
          datas['sdp_workspace_config'] = data.data.map((item) => ({
            ...item,
            id: with_id ? item.id : null,
            workspace_name: null,
          }))
          return self.getProjectList({
            workspace_name: workspaceName,
            query_options: {
              order_by: [{ workspace_name: 'asc' }, { name: 'asc' }],
            },
          })
        })
        .then((data) => {
          datas['sdp_project'] = data.data.map((item) => ({
            ...item,
            id: with_id ? item.id : null,
            workspace_name: null,
          }))
          let root_path_common = ''
          let root_path_common_char_index = 0
          for ( let root_path_common_char_index = 0; root_path_common_char_index < 200; root_path_common_char_index++) {
            let root_path_common_last_char = undefined
            let isDiff = false
            for(let project in datas['sdp_project']) {
              let root_path = datas['sdp_project'][project].root_path
              if (!root_path || root_path.indexOf('(root)') == 0) {
                isDiff = true;
                continue;
              }
              if (root_path_common_char_index >= root_path.length) {
                isDiff = true;
                break;
              }
              let current_char = root_path.substring(root_path_common_char_index, root_path_common_char_index + 1);
              if (root_path_common_last_char === undefined) {
                root_path_common_last_char = current_char
              } else {
                if (root_path_common_last_char != current_char) {
                  isDiff = true;
                  break;
                }
              }
            }
            if (isDiff) {
              break;
            } else {
              root_path_common += root_path_common_last_char
            }
          }
          console.log('root_path_common', root_path_common)
          if (root_path_common && !with_id) {
            let last_char = root_path_common.substring(root_path_common.length - 1)
            let more_char = ''
            if (last_char == '/' || last_char == '\\') {
              more_char = last_char
            }
            datas['sdp_project'] = datas['sdp_project'].map( item => {
              let root_path = item.root_path
              if (root_path && root_path.indexOf(root_path_common) == 0) {
                item.root_path = '(root)' + more_char + root_path.substring(root_path_common.length)
              }
              return item;
            })
          }
          return self.getTemplateList({
            workspace_name: workspaceName,
            query_options: {
              order_by: [
                { workspace_name: 'asc' },
                { project_name: 'asc' },
                { name: 'asc' },
                { file_type: 'asc' },
                { project: 'asc' },
                { package_name: 'asc' },
              ],
            },
          })
        })
        .then((data) => {
          datas['sdp_template'] = data.data.map((item) => {
            item = {
              ...item,
              id: with_id ? item.id : null,
              workspace_name: null,
            }
            let itemNames = ['file_template', 'remark']
            itemNames.forEach((itemName) => {
              let lines = item[itemName]
              let destLines = []
              if (lines) {
                lines = lines.split('\n')
                for (let line in lines) {
                  line = lines[line]
                  destLines.push(line)
                }
                item[itemName] = destLines
              }
            })
            itemNames = ['extra_info']
            itemNames.forEach((itemName) => {
              let lines = item[itemName]
              if (lines) {
                try {
                  item[itemName] = JSON.parse(lines)
                } catch (ex) {}
              }
            })
            return item
          })
          return self.getSqlList({
            workspace_name: workspaceName,
            query_options: {
              order_by: [
                { workspace_name: 'asc' },
                { table_name: 'asc' },
                { parameter_catalog: 'asc' },
                { parameter_catalog_type: 'asc' },
                { name: 'asc' },
              ],
            },
          })
        })
        .then((data) => {
          datas['sdp_sql'] = data.data.map((item) => {
            item = {
              ...item,
              id: with_id ? item.id : null,
              workspace_name: null,
            }
            let itemNames = ['parameter_sql', 'java_imports', 'remarks']
            itemNames.forEach((itemName) => {
              let lines = item[itemName]
              let destLines = []
              if (lines) {
                lines = lines.split('\n')
                for (let line in lines) {
                  line = lines[line]
                  destLines.push(line)
                }
                item[itemName] = destLines
              }
            })
            itemNames = ['extra_info']
            itemNames.forEach((itemName) => {
              let lines = item[itemName]
              if (lines) {
                try {
                  item[itemName] = JSON.parse(lines)
                } catch (ex) {}
              }
            })
            return item
          })
        })
        .then((data) => {
          return datas
        })
    })
}
