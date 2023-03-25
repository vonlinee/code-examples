<template>
  <div id="editor-container" style="width: 100%; height: 800px">

  </div>
</template>

<script>
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';

export default {
  name: "MonacoEditor",
  data: function () {
    return {
      editor: null
    }
  },
  methods: {
    initMonacoEditor() {
      return monaco.editor.create(document.getElementById('editor-container'), {
        value: `const foo = () => 0;`,
        language: 'freemarker2',
        theme: 'vs'
      })
    }
  },
  mounted() {
    console.log("mounted")
    this.editor = this.initMonacoEditor()
  }
}
</script>

<style scoped>

</style>