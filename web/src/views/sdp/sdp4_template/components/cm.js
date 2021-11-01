/* eslint-disable */
// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: https://codemirror.net/LICENSE

;(function (mod) {
  if (typeof exports == 'object' && typeof module == 'object')
    // CommonJS
    mod(require('codemirror/lib/codemirror'))
  else if (typeof define == 'function' && define.amd)
    // AMD
    define(['codemirror/lib/codemirror'], mod)
  // Plain browser env
  else mod(CodeMirror)
})(function (CodeMirror) {
  'use strict'

  var spaceStrs = ['']
  function spaceStr(n) {
    while (spaceStrs.length <= n) {
      spaceStrs.push(lst(spaceStrs) + ' ')
    }
    return spaceStrs[n]
  }
  function lst(arr) {
    return arr[arr.length - 1]
  }

  CodeMirror.commands.insertTab = function (cm) {
    var spaces = [],
      ranges = cm.listSelections(),
      tabSize = cm.options.tabSize
    for (var i = 0; i < ranges.length; i++) {
      var pos = ranges[i].from()
      var col = CodeMirror.countColumn(cm.getLine(pos.line), pos.ch, tabSize)
      spaces.push(spaceStr(tabSize - (col % tabSize)))
    }
    cm.replaceSelections(spaces)
  }
  window.CodeMirror = CodeMirror
})
