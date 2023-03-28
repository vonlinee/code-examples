<template>
  <div>
    <el-button type="text" @click="flag = true">点击打开 Dialog</el-button>

    <div id="editor1">
      <monaco-editor pid="editor1"></monaco-editor>
    </div>
    <div id="editor2">
      <monaco-editor pid="editor2"></monaco-editor>
    </div>

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

    <el-dialog title="提示" :visible.sync="flag">
      <div id="editor3" style="background-color: red">
        <monaco-editor pid="editor3"></monaco-editor>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import CodeMirrorEditor from '~/components/editor/CodeMirrorEditor.vue';
import MonacoEditor from '~/components/editor/MonacoEditor.vue';

export default {
  components: {
    CodeMirrorEditor,
    MonacoEditor
  },
  data() {
    return {
      dialogVisible: false,
      flag: false,
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => {
        done();
      }).catch(_ => {
      });
    },
  },
};
</script>
