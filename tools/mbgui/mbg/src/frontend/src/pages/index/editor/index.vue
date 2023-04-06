<template>
  <div>
    <el-button @click="open">点我打开</el-button>
    <Dialog ref="dialog" :config="config" :beforeClose="beforeClose" @close="resetForm">
        <VueCM></VueCM>
    </Dialog>
  </div>
</template>
<script>
// import CodeMirrorEditor from '~/components/editor/CodeMirrorEditor.vue';
import VueCM from '~/components/editor/VueCM.vue';
import Dialog from "@/components/Dialog.vue";

export default {
  components: {
    Dialog, VueCM
  },
  data() {
    return {
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
    open() {
      this.$refs.dialog.open(cancel => {
        // cancel();
        console.log('点击提交按钮了')
      })
      .then(() => {console.log(this.$refs.span)});   //这里就充分利用了open方法中返回的nextTick
    },
    beforeClose(){
      console.log('关闭前');
    },
    resetForm(){
      // 这里可以写重置表单的实现
    },
  }
};
</script>
