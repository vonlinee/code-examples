<template>
  <div class="first">
    First Page
  </div>
</template>

<script>
export default {
  name: 'FirstPage',
  props: {
    msg: String
  }
}
</script>

<!-- 添加scoped属性可以限制CSS只应用于当前组件 -->
<style scoped>
</style>
