<template>
  <div>
    <div ref="codeContainer" class="editor-container"></div>
  </div>
</template>

<script>
import * as monaco from 'monaco-editor';

export default {
  name: "MonacoEditor",
  data() {
    return {
      monacoEditor: null, // 语言编辑器
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      if (this.$refs.codeContainer) { // 初始化编辑器，确保dom已经渲染
        this.monacoEditor = monaco.editor.create(this.$refs.codeContainer, {
          value: '{}', // 编辑器初始显示文字
          language: 'json', // 语言
          automaticLayout: true, // 自动布局
          theme: 'vs', // 官方自带三种主题vs, hc-black, or vs-dark
          minimap: { // 关闭小地图
            enabled: false,
          }
        });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.editor-container {
  width: 100%;
  height: 160px;
  border: 1px solid rgba(0, 0, 0, 0.08);
}
</style>