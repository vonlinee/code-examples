<template>
  <div class="hello" style="width: 400px; height: 400px">
    <router-link :to="{name:'login', params:{id:1}}">[text]</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "HelloWorld"
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello {
  background-color: gold;
}

button {
  background-color: red;
}
</style>
