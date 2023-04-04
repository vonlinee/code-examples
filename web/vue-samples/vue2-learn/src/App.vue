<template>
  <div id="app">
    <!-- <button @click="show">show</button>
    <HelloWorld msg="message" :callbackFunction="showValue" ref="hw" /> -->

    <CodeMirrorEditor></CodeMirrorEditor>
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import CodeMirrorEditor from './components/cm/CodeMirrorEditor.vue'
export default {
  name: 'App',
  components: {
    CodeMirrorEditor
  },
  data: function () {
    return {
      parentData: "111"
    }
  },
  methods: {
    show() {
      console.log(this.$refs.hw)
    },
    showValue(val) {
      console.log("子组件调用回调函数:", val)
    }
  },
  mounted() {
    // this.$refs.hw.$on('event', function (val) {
    //   console.log(val)
    // })
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #135ea9;
  margin-top: 60px;
}
</style>
