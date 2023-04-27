<template>
  <div>
    {{ student.age }}
  </div>

  <button @click="show">年龄</button>
</template>

<script>
import { ref, reactive } from "vue";

import { onBeforeMount, onMounted } from "vue";

export default {
  name: "App",
  components: {},
  setup() {
    onBeforeMount(() => {
      console.log("V3 beforeMount!");
    });

    let m = reactive({
      name: "ll",
      age: 23,
    });

    console.log(m)

    onMounted(() => {
      console.log("V3 mounted!");
    });

    let student = ref({
      name: "zs",
      age: 20,
    });
    function show() {
      student.value.age++;
    }

    return {
      student,
      show,
    };
  },
};
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
