<template>
  <div id="app">
    <SchoolVue v-on:myEvent="onMyEvent"></SchoolVue>
  </div>
</template>

<script>

import SchoolVue from './components/School.vue';

export default {
  name: 'App',
  components: {
    SchoolVue
  },
  methods: {
    onMyEvent() {
      console.log(this)
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
  color: #2c3e50;
  margin-top: 60px;
}
</style>
