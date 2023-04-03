<!--https://www.cnblogs.com/tnnyang/p/13596099.html-->
<template>
    <el-dialog
            class="el-dialog-cus"
            v-bind="attributes"
            :visible="visible"
            :before-close="beClose"
            append-to-body
            :close-on-click-modal="false"
            v-on="on">
        <!-- 防止在弹窗中嵌套一些其他组件时，那些组件的生命周期只会执行一次的问题出现 -->
        <div class="sync-dialog__div">
            <slot v-if="visibleSlot"></slot>
        </div>
        <div slot="footer">
            <el-button @click="cancel" plain>{{ btnTxt[0] }}</el-button>
            <el-button @click="confirm" type="primary" v-if="btnTxt[1]">{{ btnTxt[1] }}</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    inheritAttrs: false,
    props: {
        config: Object,
        autoClose: {
            type: Boolean,
            default: true,
        },
        beforeClose: {
            type: Function,
            default: () => {
            },
        },
    },
    data() {
        const {
            top = '20vh',
            width = '60%',
            title = '提示',
            center = false,
            btnTxt = ["取消", "确定"]
        } = this.config || {};
        return {
            visible: false,
            attributes: {
                top,
                width,
                title,
                center,
                ...this.config,
            },
            btnTxt,
            on: this.getDialogEvents(),
            visibleSlot: false,
        };
    },
    methods: {
        open(ok) {
            this.ok = ok;
            this.visible = true;
            this.visibleSlot = true;
            return this.$nextTick();
        },
        cancel() {
            this.visible = false;
        },
        confirm() {
            let cancel = () => this.cancel();
            this.ok(cancel);
            this.autoClose && cancel();
        },
        beClose(done) {
            done();
            this.beforeClose();
            this.cancel();
        },
        getDialogEvents() {
            // closed: () => this.visibleSlot = false是为了防止弹窗中的内容先于弹窗消失而造成弹窗在关闭时有一个突然向上缩小的情况
            let {close} = this.config || {}, events = {closed: () => this.visibleSlot = false};
            if (close && typeof close == 'function') {
                Object.assign(events, {close,})
            }
            return events
        },
    },
};
</script>

<style lang="scss">

.sync-dialog__div {
  height: 300px;
  overflow: auto;
}

.el-dialog-cus {
  .el-dialog {
    padding: 8px;
  }

  .el-dialog__title {
    font-weight: bold;
  }

  .el-dialog__header {
    padding: 20px 0 12px;
  }

  .el-dialog__headerbtn {
    top: 8px;
    right: 8px;
  }

  .el-dialog__body {
    padding: 0 24px;
  }

  .el-dialog__footer {
    padding: 20px;

    .el-button {
      padding: 8px 20px;

      & + .el-button {
        margin-left: 40px;
      }
    }
  }
}
</style>
