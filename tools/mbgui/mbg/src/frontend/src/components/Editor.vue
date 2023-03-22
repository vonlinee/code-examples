<template>
  <textarea ref="mycode" class="codesql" v-model="code" style="height:200px;width:600px;"></textarea>
</template>

<script>
import "codemirror/theme/ambiance.css";
import "codemirror/lib/codemirror.css";
import "codemirror/addon/hint/show-hint.css";

import vcm from "vue-codemirror";

console.log(vcm)

import 'codemirror/addon/edit/matchbrackets'
import 'codemirror/addon/selection/active-line'

import 'codemirror/mode/sql/sql'
import 'codemirror/addon/hint/show-hint'
import 'codemirror/addon/hint/sql-hint'

export default {
  name: "Editor",
  data() {
    return {
      code: ''
    }
  },
  mounted() {
    let mime = 'text/x-mariadb'
    //let theme = 'ambiance'//设置主题，不设置的会使用默认主题
    let editor = vcm.CodeMirror.fromTextArea(this.$refs.mycode, {
      mode: mime,//选择对应代码编辑器的语言，我这边选的是数据库，根据个人情况自行设置即可
      indentWithTabs: true,
      smartIndent: true,
      lineNumbers: true, // 是否显示行号
      matchBrackets: true,
      theme: 'yeti',
      autofocus: true
    })
  }
}
</script>

<style>
.codesql {
  font-size: 11pt;
  font-family: Consolas, Menlo, Monaco, Lucida Console, Liberation Mono, DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace, serif;
}
</style>