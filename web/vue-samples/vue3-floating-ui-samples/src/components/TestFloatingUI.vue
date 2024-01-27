<script setup>
import {ref} from 'vue';
import {useFloating} from '@floating-ui/vue';
 
const reference = ref(null);
const floating = ref(null);
const {floatingStyles} = useFloating(reference, floating);
</script>
 
<template>
  <button ref="reference">Button</button>
  <div ref="floating" :style="floatingStyles">
    <ul>
      <li>1</li>
      <li>1</li>
      <li>1</li>
      <li>1</li>
      <li>1</li>
    </ul>
  </div>
</template>