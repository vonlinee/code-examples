<template>
  <div id="app">
    <LoginView id="c1" ref="myRef"></LoginView>
    <VuexTest id="c2"></VuexTest>
    <button @click="showRefNode">showRefNode</button>
  </div>
</template>

<script>
import LoginView from "@/components/LoginView.vue";
import VuexTest from "@/components/VuexTest.vue";

export default {
  name: 'App',
  components: {VuexTest, LoginView},
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

