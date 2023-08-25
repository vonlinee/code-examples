<template>
<splitpanes class="default-theme" style="height: 400px">
  <pane min-size="20" max-size="70">
    <el-tree :data="data" :props="defaultProps" default-expand-all @node-click="handleNodeClick" style="width: 100%; height: 100%;"/>
  </pane>
  <pane>
    <el-input
    style="width: 100%; height: 100%;"
    v-model="textarea"
    type="textarea"
    :autosize="{ minRows: 20, maxRows: 20 }"
    placeholder="Please input"
  />
  </pane>
</splitpanes>

</template>

<script setup lang="ts">
import { Splitpanes, Pane } from 'splitpanes'
import 'splitpanes/dist/splitpanes.css'
import { ref } from 'vue';

const textarea = ref('')

interface Tree {
  label: string
  children?: Tree[]
}

const handleNodeClick = (data: Tree) => {
  textarea.value = data.label
}

const data: Tree[] = [
  {
    label: 'Level one 1',
    children: [
      {
        label: 'Level two 1-1',
        children: [
          {
            label: 'Level three 1-1-1',
          },
        ],
      },
    ],
  },
  {
    label: 'Level one 2',
    children: [
      {
        label: 'Level two 2-1',
        children: [
          {
            label: 'Level three 2-1-1',
          },
        ],
      },
      {
        label: 'Level two 2-2',
        children: [
          {
            label: 'Level three 2-2-1',
          },
        ],
      },
    ],
  },
  {
    label: 'Level one 3',
    children: [
      {
        label: 'Level two 3-1',
        children: [
          {
            label: 'Level three 3-1-1',
          },
        ],
      },
      {
        label: 'Level two 3-2',
        children: [
          {
            label: 'Level three 3-2-1',
          },
        ],
      },
    ],
  },
]

const defaultProps = {
  children: 'children',
  label: 'label',
}


</script>

<style scoped lang="scss">
.splitpanes__pane {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: Helvetica, Arial, sans-serif;
  color: rgba(85, 91, 82, 0.6);
  font-size: 5em;
}

.splitpanes__splitter {
  width: 200px;
}
</style>