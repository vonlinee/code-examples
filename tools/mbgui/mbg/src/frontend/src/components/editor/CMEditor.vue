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
// 引入主题 可以从 codemirror/theme/ 下引入多个
import 'codemirror/theme/idea.css';
import 'codemirror/theme/eclipse.css';
import 'codemirror/theme/darcula.css';
import 'codemirror/theme/blackboard.css';
import 'codemirror/theme/3024-day.css';
import 'codemirror/lib/codemirror.css';
import 'codemirror/mode/javascript/javascript.js';

import {codemirror} from 'vue-codemirror';
// 引入语言模式 可以从 codemirror/mode/ 下引入多个
import 'codemirror/mode/sql/sql.js';
import 'codemirror/mode/yaml/yaml.js';
import 'codemirror/mode/clike/clike.js';
import 'codemirror/mode/xml/xml.js';
import 'codemirror/theme/material.css';

import 'codemirror/addon/selection/active-line'; //光标行背景高亮，配置里面也需要styleActiveLine设置为true
import 'codemirror/keymap/sublime'; //sublime编辑器效果
import '@/assets/js/freemarker.js';

export default {
  name: 'CMEditor',
  components: {
    codemirror,
  },
  data() {
    return {
      code: 'select a from table1 where b = 1',
      cmOptions: {
        mode: 'freemarker', // 语言及语法模式
        // 主题
        theme: 'eclipse',
        // 显示函数
        line: true,
        indentUnit: 4, // 缩进多少个空格
        tabSize: 4, // 制表符宽度
        lineNumbers: true, // 是否显示行号
        lineWrapping: true, // 是否默认换行
        firstLineNumber: 1, // 在哪个数字开始计数行。默认值为1
        readOnly: false, // 禁止用户编辑编辑器内容
        smartIndent: true, // 智能缩进
        font: {
          size: 30,
        },
      },
    };
  },
  methods: {
    inputChange(content) {
      this.$nextTick(() => {
        console.log('code:' + this.code);
        console.log('content:' + content);
      });
    },
  },
};
</script>

<style scoped>
.CodeMirror * {
  font-family: cursive;
  font-size: 32px;
}
</style>