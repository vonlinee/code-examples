<template>
  <div>
    <div class="content-header"></div>
    <div class="content">
      <div class="container">
        <div style="margin-bottom: 10px">
          <el-select value="choosenConnectionName" v-model="choosenConnectionName" clearable placeholder="请选择"
                     @change="onConnectionChange"
                     filterable allow-create default-first-option>
            <el-option
                v-for="item in connectionInfoList"
                :key="item.connectionId"
                :label="item.connectionName"
                :value="item.connectionName"
            >
            </el-option>
          </el-select>
          <el-select value="choosenDbName" v-model="choosenDbName" clearable placeholder="请选择"
                     @change="onDbChange" allow-create default-first-option>
            <el-option
                v-for="(item, index) in databaseNames"
                :key="index"
                :label="item"
                :value="item"></el-option>
          </el-select>
          <el-input v-model="searchKey" inline filterable clearable placeholder="请输入"></el-input>
          <span style="color: #e6a23c; font-size: 14px">
            <a href="#" @click="openImportProjectView">导入自定义配置</a>
            <router-link to="/config">自定义配置信息</router-link>
            <a href="#" @click="openGenSetting">项目配置</a>
          </span>
        </div>
        <el-table
            ref="tableList"
            :data="queryTables"
            border
            style="width: 100%"
            @selection-change="handleTableSelection">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column
              type="index"
              width="50"
              label="序号"
          ></el-table-column>
          <el-table-column sortable prop="name" label="table名称">
            <template v-slot="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column prop="comment" label="table注释"></el-table-column>
        </el-table>
        <el-button type="primary" @click="openGenSetting">代码生成</el-button>
      </div>
    </div>
    <el-dialog
        :visible.sync="showGenSettingDialog"
        title="输出配置"
        width="80%"
        top="5vh">
      <el-tabs active-name="base">
        <el-tab-pane label="基本配置" name="base">
          <el-form label-width="120px">
            <el-form-item label="代码作者">
              <el-input v-model="genSetting.author" style="width: 260px"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="功能模块名">
                  <el-input
                      v-model="genSetting.moduleName"
                      placeholder="模块名将加入到输出包名之后，用于划分不同的模块"
                      style="width: 400px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="基础包名">
                  <el-input
                      v-model="genSetting.basePackageName"
                      placeholder="基础包名"
                      style="width: 400px"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="需生成的文件">
              <el-checkbox-group v-model="genSetting.choosedOutputFiles">
                <el-checkbox
                    v-for="file in userConfig.outputFiles"
                    :label="file.fileType"
                    :key="file.fileType"
                >{{ file.fileType }}
                </el-checkbox
                >
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="需生成的Controller方法" v-if="isControllerChecked">
              <el-alert
                  title="注意：如果更换了Controller的模板，以下选项可能不会生效，需自行在模板中实现"
                  type="warning"
              ></el-alert>
              <el-checkbox-group v-model="genSetting.choosedControllerMethods">
                <el-checkbox label="list" key="list">列表查询</el-checkbox>
                <el-checkbox label="getById" key="getById">按ID查询</el-checkbox>
                <el-checkbox label="create" key="create">新增</el-checkbox>
                <el-checkbox label="update" key="update">修改</el-checkbox>
                <el-checkbox label="delete" key="delete">删除</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label-width="150px" label="是否覆盖同名文件">
                  <el-switch v-model="genSetting.override"></el-switch>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label-width="150px" label="是否打开根目录">
                  <el-switch v-model="genSetting.showRoot"></el-switch>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="项目根目录">
              <el-input
                  v-model="genSetting.rootPath"
                  style="width: 400px"
              ></el-input>
              <help-tip
                  content="最终生成的代码位置等于：项目根目录 + 具体某个类别的源码设置的包目录"
              ></help-tip>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="genCode()">开始生成</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="模板配置" name="tmplate_config">
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <el-dialog
        :visible.sync="showSavedProjectsDialog"
        title="项目配置导入"
        width="50%"
        top="5vh">
      <el-table :data="savedProjects" height="300px">
        <el-table-column label="项目包路径">
          <template slot-scope="scope">
            {{ scope.row }}
          </template>
        </el-table-column>
        <el-table-column label="导入">
          <template slot-scope="scope">
            <el-button
                type="info"
                size="mini"
                @click="importProjectConfig(scope.row)"
            >导入
            </el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import axios from "axios";
import _ from "lodash";
import {Loading} from "element-ui";
import HelpTip from "@/components/HelpTip";

export default {
  props: [],
  components: {
    HelpTip,
  },
  data() {
    return {
      connectionInfoList: [
        {
          connectionName: "",
          connectionId: ""
        }
      ],
      databaseNames: [],
      choosenConnectionName: undefined,
      choosenDbName: undefined,
      searchKey: "",
      showGenSettingDialog: false,
      tables: [],
      choosedTables: [],
      genSetting: {
        author: "",
        choosedOutputFiles: [],
        override: false,
        showRoot: true,
        moduleName: "",
        choosedControllerMethods: [],
        rootPath: "",
        basePackageName: undefined
      },
      userConfig: {},
      outputFileInfos: [],
      showSavedProjectsDialog: false,
      savedProjects: [],
    };
  },
  computed: {
    isControllerChecked: function () {
      if (this.genSetting.choosedOutputFiles) {
        return this.genSetting.choosedOutputFiles.indexOf("Controller") !== -1;
      }
      return false;
    },
    queryTables: function () {
      let tmp = [];
      this.tables.forEach((t, i) => {
        if (
            t.name &&
            (t.name.toLowerCase().indexOf(this.searchKey.toLowerCase()) !== -1 ||
                !this.searchKey)
        ) {
          tmp.push(t);
        }
      });
      return tmp;
    },
  },
  mounted: function () {
    this.getUserConfig();
    this.getConnectionNames().then((res => {
      // 选中第一个
      if (this.connectionInfoList && this.connectionInfoList.length !== 0) {
        this.choosenConnectionName = this.connectionInfoList[0].connectionName
        this.onConnectionChange(this.choosenConnectionName)
      }
    }))
  },
  methods: {
    onConnectionChange(val) {
      this.$api.getDatabaseNames(val).then((resp) => {
        this.databaseNames = []
        this.databaseNames = resp
        if (resp.length > 0) {
          this.choosenDbName = resp[0]
          this.onDbChange(this.choosenDbName)
        }
      })
    },
    onDbChange(dbName) {
      this.$api.getDatabaseTables(this.choosenConnectionName, dbName).then((res) => {
        this.tables = res;
      });
    },
    getConnectionNames() {
      return this.$api.getConnectionNames().then((res) => {
        this.connectionInfoList = res
      })
    },
    handleTableSelection(val) {
      this.choosedTables = val.map((t) => t.name);
    },
    getUserConfig() {
      this.$api.getUserConfig().then((res) => {
        this.userConfig = res;
      });
    },
    openGenSetting() {
      try {
        //获取上一次的生成配置
        let lastSetting = sessionStorage.getItem("gen-setting");
        if (lastSetting) {
          _.assign(this.genSetting, JSON.parse(lastSetting));
          //清空部分一次性配置
          this.genSetting.moduleName = "";
          this.genSetting.override = false;
        }
        if (!this.genSetting.rootPath) {
          this.$message.warning("项目根路径为空")
        }
      } catch (e) {
        console.error(e);
      }
      this.showGenSettingDialog = true;
    },
    genCode() {
      let message = `确认生成所选择的${this.choosedTables.length}张数据表的业务代码吗?`
      this.$confirm(message, "操作提示", {type: "warning"}).then(() => {
        this.genSetting.connectionName = this.choosenConnectionName
        this.genSetting.databaseName = this.choosenDbName
        let setting = JSON.stringify(_.clone(this.genSetting));
        sessionStorage.setItem("gen-setting", setting);

        let params = {
          genSetting: this.genSetting,
          tables: this.choosedTables
        };
        let aLoading = Loading.service({});
        this.$api.startCodeGeneration(params)
            .then((res) => {
              this.$message({message: "业务代码已生成", type: "success",});
              aLoading.close();
              // this.showGenSettingDialog = false;
              // this.choosedTables = [];
              // this.$refs.tableList.clearSelection();
            })
            .catch(() => {
              aLoading.close();
            });
      });
    },
    openImportProjectView() {
      this.showSavedProjectsDialog = true;
      axios.get("/api/output-file-info/all-saved-project").then((res) => {
        this.savedProjects = res;
      });
    },
    importProjectConfig(sourceProjectPkg) {
      this.$confirm("确定导入" + sourceProjectPkg + "的配置吗？")
          .then(() => {
            axios.post("/api/output-file-info/import-project-config/" + sourceProjectPkg)
                .then(() => {
                  this.$message.success("配置已导入");
                  this.showSavedProjectsDialog = false;
                  setTimeout(() => {
                    window.location.reload();
                  }, 2);
                });
          })
          .catch(() => {
          });
    }
    ,
  },
};
</script>
<style></style>
