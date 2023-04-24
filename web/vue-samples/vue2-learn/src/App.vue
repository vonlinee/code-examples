<template>
  <div id="app">
    <!-- <button @click="show">show</button>
    <HelloWorld msg="message" :callbackFunction="showValue" ref="hw" /> -->

    <!-- <VueCM></VueCM> -->
    <p></p>
    <p></p>

    <!-- Table
    <el-button type="text" @click="dialogTableVisible = true">打开嵌套表格的 Dialog</el-button>

    <el-dialog title="收货地址" :visible.sync="dialogTableVisible">
      <VueCM></VueCM>
    </el-dialog> -->
    <input v-focus />
  </div>
</template>

<script>
// import func from "vue-editor-bridge";
// import HelloWorld from './components/HelloWorld.vue'
// import CodeMirrorEditor from './components/cm/CodeMirrorEditor.vue'
// import VueCM from './components/cm/VueCM.vue'
export default {
  name: "App",
  components: {
    // CodeMirrorEditor,
    // VueCM
  },
  directives: {
    focus: {
      mounted: (el) => el.focus(),
    },
  },
  data: function () {
    return {
      parentData: "111",
      gridData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
      ],
      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      formLabelWidth: "120px",
    };
  },
  methods: {
    show() {
      console.log(this.$refs.hw);
    },
    showValue(val) {
      console.log("子组件调用回调函数:", val);
    },
  },
  mounted() {
    // this.$refs.hw.$on('event', function (val) {
    //   console.log(val)
    // })
  },
};
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #135ea9;
  margin-top: 60px;
}
</style>
