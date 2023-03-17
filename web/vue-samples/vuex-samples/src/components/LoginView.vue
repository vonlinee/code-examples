<template>
  <div>
    <h1>这是 Login 组件</h1>
    <h1>count : {{ $store.state.num }}</h1>
    <button id="btn" @click="mutation">mutation</button>
    <button id="btn" @click="doAction">action</button>
    <button id="btn" @click="showRefNode">showRef</button>
  </div>
</template>

<script lang="ts">

import {mapState} from "vuex";

let result = mapState(['num', "userinfo"])
console.log(result)
// let userinfo = mapState(['userinfo'])
// console.log(userinfo)

export default {
  name: "LoginView",
  methods: {
    change() {
      this.$store.state.num = 200 //不推荐这种写法,vuex所有修改数据都要写在mutations里
    },
    mutation() {
      let currentNum = this.$store.state.num;
      this.$store.commit('setNum', currentNum + 1)
    },
    doAction() {
      let currentNum = this.$store.state.num;
      this.$store.dispatch("changeNum", currentNum + 1)
    }
  }
}
</script>

<style scoped>

</style>
