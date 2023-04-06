<template>
  <div>
      <codemirror ref="jsonEditor" v-model="jsonData" :options="options"/>
  </div>
</template>

<script>
// require component
import {codemirror} from 'vue-codemirror'
// import jsonlint from 'jsonlint' //lint验证需要的组件，在下面详细讲

// require styles
import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/javascript/javascript' // 这js模式必须引入的
import 'codemirror/addon/selection/active-line' //光标行背景高亮，配置里面也需要styleActiveLine设置为true
import 'codemirror/keymap/sublime' //sublime编辑器效果
import 'codemirror/mode/css/css'
import 'codemirror/theme/monokai.css' //编辑器主题样式，配置里面theme需要设置monokai
//下面这几个引入的主要是验证提示类的,配置里的lint需要设置true,gutters: ['CodeMirror-lint-markers']
import 'codemirror/addon/lint/lint'
import 'codemirror/addon/lint/lint.css'
import 'codemirror/addon/lint/json-lint'
import 'codemirror/addon/display/autorefresh' //及时自动更新，配置里面也需要设置autoRefresh为true

// component
export default {
  components: {
    codemirror
  },
  data: function () {
    return {
      jsonData: '',
      options: {
        mode: 'application/json', //编辑器语言
        theme: 'eclipse', 	//编辑器主题
        extraKeys: {"Ctrl": "autocomplete"},	//ctrl可以弹出选择项
        lineNumbers: true,	//显示行号
        tabSize: 4,// tab的空格个数
        lineWrapping: true, //是否自动换行
        styleActiveLine: true,//line选择是是否加亮
        matchBrackets: true,//括号匹配
        readOnly: false,//只读
        autoRefresh: true // 自动刷新
      }
    }
  }
}
</script>




