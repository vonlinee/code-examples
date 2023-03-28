<template>
  <div id="app" style="display: inline-block">
    <LoginView id="c1" ref="myRef"></LoginView>
    <VuexTest id="c2"></VuexTest>
    <button @click="showRefNode">showRefNode</button>

    <h1>1111111111111111</h1>
    <h1>1111111111111111</h1>
    <h1>1111111111111111</h1>

    <MyComponent1></MyComponent1>
  </div>
</template>

<script>
import LoginView from "@/components/LoginView.vue";
import VuexTest from "@/components/VuexTest.vue";

import MyComponent1 from "@/components/MyComponent1.vue";

export default {
  name: 'App',
  components: {VuexTest, LoginView, MyComponent1},
  methods: {
    showRefNode() {
      let node = this.$refs.myRef;
      console.log(node)
    }
  }
}
</script>

<style>
#app {
  display: flex;
  padding-inline: 55px;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

#c1 {
  background-color: red;
  width: 200px;
  height: 200px;
}

#c2 {
  background-color: green;
  width: 200px;
  height: 200px;
}
</style>

