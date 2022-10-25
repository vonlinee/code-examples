<template>
  <div class="first">
    Second Page
  </div>
</template>

<script>
export default {
  name: 'SecondPage',
  props: {
    msg: String
  }
}
</script>

<!-- 添加scoped属性可以限制CSS只应用于当前组件 -->
<style scoped>
</style>
