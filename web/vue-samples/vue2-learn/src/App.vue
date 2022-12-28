<template>
  <div id="app">
    <button @click="show">show</button>
    <HelloWorld msg="`${this.parentData}`" ref="hw" :title="标题"/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld
  },
  data: function() {
    return {
      parentData: "111"
    }
  },
  methods: {
    show() {
      console.log(this.$refs.hw)
    }
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
