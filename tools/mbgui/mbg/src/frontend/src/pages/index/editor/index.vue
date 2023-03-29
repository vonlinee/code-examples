<template>
    <div>
        <el-button type="text" @click="flag = true">点击打开 Dialog</el-button>

        <monaco-editor container="editor1"></monaco-editor>

        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">
            <span>这是一段信息</span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
        </el-dialog>

        <el-dialog title="提示" :visible.sync="flag" ref="editorDialog">
            <el-button @click="showDialogSize()">btn1</el-button>
            <monaco-editor container="editor55"></monaco-editor>
        </el-dialog>

        <div>
            <el-button @click="open">点我打开</el-button>
            <Dialog ref="dialog" :config="config" :beforeClose="beforeClose" @close="resetForm">
                <monaco-editor container="div111"></monaco-editor>
            </Dialog>
        </div>
    </div>
</template>
<script>
import CodeMirrorEditor from '~/components/editor/CodeMirrorEditor.vue';
import MonacoEditor from '~/components/editor/MonacoEditor.vue';
import Dialog from '~/components/Dialog.vue';

export default {
    components: {
        CodeMirrorEditor,
        MonacoEditor,
        Dialog
    },
    data() {
        return {
            dialogVisible: false,
            flag: false,
            config: {
                top: '20vh',
                width: '500px',
                title: '温馨提示',
                center: true,
                btnTxt: ['取消', '提交'],
            },
        };
    },
    methods: {
        handleClose(done) {
            this.$confirm('确认关闭？').then(_ => {
                done();
            }).catch(_ => {
            });
        },
        showDialogSize() {
            console.log(this.$refs.editorDialog);
        },
        open() {
            this.$refs.dialog.open(cancel => {
                // cancel();
                console.log('点击提交按钮了')
            }).then(() => {
                console.log(this.$refs.span)
            });   //这里就充分利用了open方法中返回的nextTick
        },
        beforeClose() {
            console.log('关闭前');
        },
        resetForm() {
            // 这里可以写重置表单的实现
        },
    },
};
</script>
