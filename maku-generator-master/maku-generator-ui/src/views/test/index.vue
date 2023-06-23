<script setup lang="ts">

import CodeEditor from "@/components/CodeEditor.vue";
import {ElButton} from "element-plus";
import {ref} from "vue";

let inputRef = ref()
let btn1Ref = ref()
let editorRef = ref()

let inputTextRef = ref()

function setText() {
	console.log(editorRef.value);
}
</script>

<template>
	<code-editor mode='sql' ref="editorRef"></code-editor>
	<el-button type="info" ref="btn1Ref" @click="setText">设置文本</el-button>
	<el-input ref="inputRef" v-model="inputTextRef"></el-input>
</template>

<style scoped lang="scss">

</style>