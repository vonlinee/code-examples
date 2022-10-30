<template>
  <div class="hello">
    <button @click="$emit('myEvent')">click me</button>
  </div>
</template>

<script>
export default {
  name: 'SchoolComponent'
}
</script>

<style scoped>

</style>
