<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button @click="triggerEvent()">Button</button>
  </div>
</template>

<script>

export default {
  name: 'HelloWorld',
  props: {
    msg: {
      type: String
    },
    callbackFunction: {
      type: Function
    }
  },
  methods: {
    triggerEvent() {
      this.$emit('myevent', {
        key1: "value1",
        key2: "value2"
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
button {
  background-color: red;
}
</style>
