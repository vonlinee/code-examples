<template>
  <dragTreeTable :data="treeData" :onDrag="onTreeDataChange"></dragTreeTable>

</template>

<script>
import dragTreeTable from "drag-tree-table";

export default {
  name: "app",
  data() {
    return {
      treeData: {
        columns: [
          {
            title: "名称",
            field: "name"
          }, {
            title: "名称",
            field: "name"
          }
        ],
        lists: [
          {
            "id": 40,
            "parent_id": 0,
            "order": 0,
            "name": "动物类",
            "uri": "/masd/ds",
            "open": true,
            "lists": []
          }, {
            "id": 5,
            "parent_id": 0,
            "order": 1,
            "name": "昆虫类",
            "uri": "/masd/ds",
            "open": true,
            "isShowCheckbox": false, // '如果设置了type=checkbox,但是想控制某些行不显示CheckBox，可以用此属性'
            "lists": [
              {
                "id": 12,
                "parent_id": 5,
                "open": true,
                "order": 0,
                "name": "蚂蚁",
                "uri": "/masd/ds",
                "lists": []
              }
            ]
          },
          {
            "id": 19,
            "parent_id": 0,
            "order": 2,
            "name": "植物类",
            "uri": "/masd/ds",
            "open": true,
            "lists": []
          }
        ]
      }
    };
  },
  components: {
    dragTreeTable
  },
  methods: {
    onTreeDataChange(list) {
      this.treeData.lists = list;
    }
  }
};
</script>