<template>
    <codemirror class="code" v-model="code" :options="cmOptions"></codemirror>
</template>

<script>
import {codemirror} from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/keymap/sublime' //sublime编辑器效果
import "codemirror/theme/eclipse.css"// 配置里面也需要theme设置相应的主题
import "codemirror/mode/vue/vue.js" // 配置里面也需要mode设置为vue
import 'codemirror/addon/selection/active-line' //光标行背景高亮，配置里面也需要styleActiveLine设置为true

// 引入自动刷新  https://blog.csdn.net/yanmuchen/article/details/121930754
import 'codemirror/addon/display/autorefresh'

export default {
    name: "CodeMirrorEditor",
    components: {
        codemirror
    },
    data() {
        return {
            // 代码
            code: "",
            cmOptions: {
                tabSize: 4,// tab的空格个数
                theme: 'eclipse',//主题样式
                lineNumbers: true,//是否显示行数
                lineWrapping: true, //是否自动换行
                styleActiveLine: true,//line选择是是否加亮
                matchBrackets: true,//括号匹配
                mode: "vue", //实现javascript代码高亮
                readOnly: false//只读
            }
        }
    }
}
</script>

<style lang="scss">
.CodeMirror {
  height: 100%;
}
</style>