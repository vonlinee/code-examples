<!-- 引自:https://blog.csdn.net/weixin_64141611/article/details/123865478 -->
<template>
	<textarea id="editor"></textarea>
</template>

<script>
// 正常引入实例
import CodeMirror from 'codemirror';
// 核心样式
import 'codemirror/lib/codemirror.css';
// 引入代码块主题  我这边是动态主题，所以引入的有点多
// 正常下面设置 theme: '3024-day' 就好了
// import 'codemirror/theme/eclipse.css';

// 需要引入具体的语法高亮库才会有对应的语法高亮效果
// codemirror 官方其实支持通过 /addon/mode/loadmode.js 和 /mode/meta.js 来实现动态加载对应语法高亮库
// 但 vue 貌似没有无法在实例初始化后再动态加载对应 JS ，所以此处才把对应的 JS 提前引入

import 'codemirror/mode/javascript/javascript.js';


export default {
	mounted() {
		let textArea = document.getElementById('editor')
		let CodeMirrorEditor = CodeMirror.fromTextArea(textArea, {
			mode: 'javascript',//编辑器语言
			theme: 'eclipse', //编辑器主题
			extraKeys: { "Ctrl": "autocomplete" },//ctrl可以弹出选择项 
			lineNumbers: true,//显示行号
			autoRefresh: true
		});
		console.log(CodeMirrorEditor);
	}
};
</script>

<style scoped>
/* .CodeMirror {
	height: auto;
	widows: '100%';
	text-align: left!important;
} */
</style>

