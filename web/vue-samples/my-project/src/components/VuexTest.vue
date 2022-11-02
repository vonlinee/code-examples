<template>
  <div class="hello">
    <p>111111111111111111111</p>
    <button @click="changeState">click me</button>
  </div>
</template>

<script>
export default {
  name: 'VueTest',
  methods: {
    changeState: function() {
      this.$store.commit('increment', {
        name: "zs",
        age: 30,
        sex: false
      })
      console.log(this.$store.state.count)
    }
  }
}
</script>

<style scoped>

</style>
