<template>
  <div>
    <div id="container" width="400px" height="600px"></div> <!--宽高自行设定 -->
  </div>
</template>

<script>
import * as monaco from 'monaco-editor';


// Since packaging is done by you, you need
// to instruct the editor how you named the
// bundles that contain the web workers.
self.MonacoEnvironment = {
  getWorkerUrl: function (moduleId, label) {
    if (label === "json") {
      return "./json.worker.bundle.js"
    }
    if (label === "css") {
      return "./css.worker.bundle.js"
    }
    if (label === "html") {
      return "./html.worker.bundle.js"
    }
    if (label === "typescript" || label === "javascript") {
      return "./ts.worker.bundle.js"
    }
    return "./editor.worker.bundle.js"
  },
}

export default {
  data() {
    return {
      editor: null,//文本编辑器
    }
  },
  mounted() {
    this.initEditor();
  },
  methods: {
    initEditor() {
      // 初始化编辑器，确保dom已经渲染
      this.editor = monaco.editor.create(document.getElementById('container'), {
        value: 'console.log("Hello, world")',//编辑器初始显示文字
        language: 'java',//语言支持自行查阅demo
        automaticLayout: true,//自动布局
        theme: 'vs' //官方自带三种主题vs, hc-black, or vs-dark
      });
    },
    getValue() {
      this.editor.getValue(); //获取编辑器中的文本
    }
  }
}
</script>

<style></style>