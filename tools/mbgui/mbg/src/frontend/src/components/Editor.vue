<template>
  <MonacoEditor></MonacoEditor>
</template>

<script>
import MonacoEditor from "./MonacoEditor.vue";

export default {
  name: "Editor",
  components: {MonacoEditor},
  data() {
    return {
      MonacoEditor
    }
  }
}
</script>

<style>

</style>