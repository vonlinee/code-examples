<template>
  <div id="container">
  </div>
</template>
<script>
// 全局引入vue-codemirror
import VueCodeMirror from 'vue-codemirror';

// 引入主题 可以从 codemirror/theme/ 下引入多个
import 'codemirror/theme/idea.css'
// 引入语言模式 可以从 codemirror/mode/ 下引入多个
import 'codemirror/mode/sql/sql.js';

export default {
  name: "CodeMirrorEditor",
  data() {
    return {
      editor: null,
    }
  },
  methods: {
    createCodeMirrorInstance() {
      return VueCodeMirror.CodeMirror(document.getElementById('container'), {
        mode: "text/x-sql",
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets: true,
        autofocus: true,
        line: true, // 显示函数
        theme: 'idea', // 主题
        lineWrapping: true, // 软换行
        tabSize: 4, // tab宽度
        styleActiveLine: true
      })
    }
  },
  mounted() {
    this.editor = this.createCodeMirrorInstance()
    console.log("editor ", this.editor)
  }
}
</script>

<style scoped>
#container {
  width: 500px;
  height: 700px;
}

#textarea {
  font-size: 28px;
  line-height: 150%;
}
</style>