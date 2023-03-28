
<script lang="ts">
export default {
  name: 'MyComponent1',
  render(createElement) {
    console.log("11111111", this);
    
    return createElement('h1', {}, '111')
  },
  mounted() {
    
    
  }
}
</script>

<style scoped>
button {
  background-color: red;
}
</style>
