<template>
  <div>
    <!--一定要嵌套在DIV里面-->
    <div id="editor-container"></div>
  </div>
</template>

<script>
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';

export default {
  name: 'MonacoEditor',
  data: function() {
    return {
      editor: null,
    };
  },
  props: {
    'lang': {
      type: String,
      require: false,
      default() {
        return 'plain';
      },
    },
    'initialWidth': {
      type: String,
      default() {
        return '500px';
      },
    },
    'initialHeight': {
      type: String,
    },
  },
  mounted() {
    let editorContainer = document.getElementById('editor-container');
    editorContainer.style.width = this.initialWidth;
    editorContainer.style.height = this.initialHeight;
    monaco.editor.create(document.getElementById('editor-container'), {
      language: this.lang,
      theme: 'vs',
      value: 'select * from user',
    });
  },
};
</script>

<style scoped>

</style>