<template>
  <div>
    {{ ref1 }}
  </div>
  <button @click="show">年龄</button>
</template>

<script>
// import { shallowRef } from "vue";


export default {
  name: "App",
  components: {},
  setup() {


    let targetObject = {
      name: 'zs',
      info: {
        first_name: "wl"
      }
    }

    let handler = {
      get: function (target, propKey, receiver) {
        console.log(`getting ${propKey}!`);
        return Reflect.get(target, propKey, receiver);
      },
      set: function (target, propKey, value, receiver) {
        console.log(`setting ${propKey}!`);
        return Reflect.set(target, propKey, value, receiver);
      }
    }

    let proxy = new Proxy(targetObject, handler);

    console.log(proxy);

    proxy.info.first_name = 'sdd'

    return {

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
