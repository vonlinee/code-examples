<!--https://juejin.cn/post/7062245776902062116-->
<script>
import * as monaco from 'monaco-editor/esm/vs/editor/editor.main';

export default {
    name: 'MonacoEditor',
    props: {
        container: {type: String, require: true},   // Monaco Editor 绑定的容器ID
        languages: {type: String, default: 'json'},
        width: {type: String, require: false},
        height: {type: String, require: false},
        editorOptions: {
            type: Object,
            default: function () {
                return {
                    selectOnLineNumbers: true,
                    roundedSelection: false,
                    readOnly: false, // 只读
                    writeOnly: false,
                    cursorStyle: 'line', //光标样式
                    automaticLayout: true, //自动布局
                    glyphMargin: true, //字形边缘
                    useTabStops: false,
                    fontSize: 32, //字体大小
                    autoIndent: true, //自动布局
                    quickSuggestionsDelay: 500,   //代码提示延时
                };
            },
        },
        lang: {
            type: String,
            default: function () {
                return 'json';
            },
        },
    },
    data() {
        return {
            editor: null, //文本编辑器实例
            isSave: true, //文件改动状态，是否保存
            codeValue: null, //保存后的文本
        };
    },
    render(createElement) {
        return createElement('div', {
            attrs: {
                id: this.container
            },
            style: {
                // backgroundColor: "gray"
            }
        })
    },
    watch: {
        codes: function (newValue) {
            console.debug('Code editor: content change');
            if (this.editor) {
                if (newValue !== this.editor.getValue()) {
                    this.editor.setValue(newValue);
                    // 格式化文本
                    this.editor.trigger(this.editor.getValue(), 'editor.action.formatDocument');
                }
            }
        },
    },
    mounted() {
        console.log("mounted ============================")
        // 组件挂载完毕时初始化编辑器
        let div = document.getElementById(this.container);
        const self = this;
        // 初始化编辑器，确保dom已经渲染
        this.editor = monaco.editor.create(div, {
            value: self.codeValue || self.codes, // 编辑器初始显示内容
            language: self.languages, // 支持的语言
            theme: 'vs-light', // 主题
            selectOnLineNumbers: true, //显示行号
            editorOptions: self.editorOptions,
        });
        // self.$emit("onMounted", self.editor); //编辑器创建完成回调
        self.editor.onDidChangeModelContent(function (event) {
            // 编辑器内容changge事件
            self.codesCopy = self.editor.getValue();
            self.$emit('onContentChange', self.editor.getValue(), event);
        });
    },
    methods: {
        onContentChange() {
            console.log("ContentChange")
        }
    },
};
</script>

<style scoped>

</style>

