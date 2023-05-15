<template>
  <div style="width: 100%; height: 100%">
    <el-table :data="userData" :height="initialHeight" style="background-color: red;">
      <el-table-column label="Name" prop="name">
        <template #default="scope">
          <el-input
            v-if="activeIndex == scope.$index"
            v-model="scope.row.name"
          ></el-input>
          <span v-else>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Age" prop="age">
        <template #default="scope">
          <el-input
            type="number"
            v-if="activeIndex == scope.$index"
            v-model="scope.row.age"
          ></el-input>
          <span v-else>{{ scope.row.age }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Sex" prop="sex">
        <template #default="scope">
          <el-select v-if="activeIndex == scope.$index" v-model="scope.row.sex">
            <el-option label="female" value="female"></el-option>
            <el-option label="male" value="male"></el-option>
          </el-select>
          <span v-else>{{ scope.row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column align="right" width="150">
        <template #header>
          <el-button type="primary" @click="handleAdd">Add</el-button>
        </template>
        <template #default="scope">
          <div v-if="activeIndex == scope.$index">
            <el-button type="info" @click="handleSave">Save</el-button>
          </div>
          <div v-else>
            <el-row>
              <el-col :span="12">
                <el-button type="success" @click="handleEdit(scope.$index)"
                  >Edit</el-button
                >
              </el-col>
              <el-col :span="12">
                <el-popconfirm
                  @confirm="handleDelete(scope.$index)"
                  width="220"
                  confirm-button-text="OK"
                  cancel-button-text="No, Thanks"
                  :icon="InfoFilled"
                  icon-color="#626AEF"
                  title="Are you sure to delete this?"
                >
                  <template #reference>
                    <el-button type="danger">Delete</el-button>
                  </template>
                </el-popconfirm>
              </el-col>
            </el-row>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { InfoFilled } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
interface iUser {
  name: string;
  age: number;
  sex: string;
}

let initialHeight = ref("500px")

let userData: iUser[] = reactive([
  {
    name: "nico",
    age: 18,
    sex: "female",
  },
]);
let activeIndex = ref<number>(-1);
// 新增行
const handleAdd = function () {
  let item = {
    name: "",
    age: 0,
    sex: "",
  };
  userData.push(item);
  activeIndex.value = userData.length - 1;
};
// 编辑行
const handleEdit = (index: number) => {
  activeIndex.value = index;
};
// 保存行
const handleSave = () => {
  activeIndex.value = -1;
};
// 删除行
const handleDelete = function (index: number) {
  userData.splice(index, 1);
};
</script>