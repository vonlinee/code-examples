<template>
  <div id="app">
    <div>
      <router-link to="/school">School</router-link>
    </div>
    <div>
      <router-link to="/hello">Hello</router-link>
    </div>
    <div>
      <router-link to="/test">Test</router-link>
    </div>
    <router-view></router-view>

    <el-button type="success" @click="getEditorContent">getEditorContent</el-button>

    <editor ref='myEditor' v-model="content" @init="editorInit" lang="html" theme="chrome" width="500" height="100">
    </editor>
  </div>
</template>

<script>
export default {
  name: 'App',
  data: function() {
    return {
      // 编辑器的初始文本
      content: '文本信息'
    }
  },
  components: {
    editor: require('vue2-ace-editor')
  },
  methods: {
    onMyEvent() {
      console.log(this)
    },
    // 初始化Ace-Editor
    editorInit: function () {
      require('brace/ext/language_tools') //language extension prerequsite...
      require('brace/mode/html')
      require('brace/mode/javascript')    //language
      require('brace/mode/less')
      require('brace/theme/chrome')
      require('brace/snippets/javascript') //snippet
    },
    getEditorContent: function() {
      let editor = this.$refs.myEditor.editor
      editor.setTheme("ace/theme/twilight");
     

      
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
