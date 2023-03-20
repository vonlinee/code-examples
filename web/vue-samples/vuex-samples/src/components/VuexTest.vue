<template>
  <div>
    <h1>这是 Login 组件</h1>
    <h1>count : {{ $store.state.num }}</h1>
    <button id="btn" @click="show">Button</button>
  </div>
</template>

<script lang="ts">
export default {
  name: "VuexTest",
  methods: {
    change() {
      this.$store.state.num = 200 //不推荐这种写法,vuex所有修改数据都要写在mutations里
    },
    mutation() {
      this.$store.commit('setNum', 200)
    },
  }
}
</script>

<style scoped>

</style>
