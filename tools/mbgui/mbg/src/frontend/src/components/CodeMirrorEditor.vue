<template>
  <div id="container">
  </div>
</template>
<script>

import 'codemirror/theme/3024-day.css'
import 'codemirror/mode/javascript/javascript.js'

import Vue from 'vue'
import Component from 'vue-class-component'
import {codemirror} from 'vue-codemirror'

@Component({
  name: 'Demo',
  components: {
    codemirror
  }
})

export default class Demo extends Vue {
  cmOptions = {
    mode: 'application/json', // 模式
    theme: '3024-day', // 主题
    indentUnit: 4, // 缩进多少个空格
    tabSize: 4, // 制表符宽度
    lineNumbers: true, // 是否显示行号
    lineWrapping: true, // 是否默认换行
    firstLineNumber: 3, // 在哪个数字开始计数行。默认值为1
    readOnly: false, // 禁止用户编辑编辑器内容
    line: true,
    smartIndent: true // 智能缩进
  }
}

</script>

<style scoped>

</style>