<!--封装VueCodeMirror编辑器 官方文档：https://codemirror.net/5/doc/manual.html#config  -->
<template>
  <div>
    <codemirror
        id="cm-container"
        ref="cm"
        v-model="code"
        :options="cmOptions"
        @input="inputChange"
    ></codemirror>
  </div>
</template>
<script>

import "./settings"
import {codemirror} from 'vue-codemirror'

import 'codemirror/addon/selection/active-line'; //光标行背景高亮，配置里面也需要styleActiveLine设置为true

export default {
  name: 'CodeMirrorEditor',
  components: {
    codemirror,
  },
  props: {
    'mode': {
      type: String,
      default: 'javascript',
    },
  },
  data() {
    return {
      code: 'select a from t where b = 1',
      cmOptions: {
        mode: this.mode, // 语言及语法模式
        theme: 'eclipse', // 主题
        line: true, // 显示函数
        indentUnit: 4, // 缩进多少个空格
        tabSize: 4, // 制表符宽度
        lineNumbers: true, // 是否显示行号
        lineWrapping: true, // 是否默认换行
        firstLineNumber: 1, // 在哪个数字开始计数行。默认值为1
        readOnly: false, // 禁止用户编辑编辑器内容
        smartIndent: true // 智能缩进
      },
    };
  },
  methods: {
    inputChange(content) {
      this.$nextTick(() => {
        this.code = content; // 更新文本
      });
    },
    getContent() {
      return this.code;
    },
  },
  mounted() {

  }
};
</script>

<style scoped>
.CodeMirror * {
  font-family: cursive;
  font-size: 11px;
  width: 500px;
  height: 500px;
}
</style>