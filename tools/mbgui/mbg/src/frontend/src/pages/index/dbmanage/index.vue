<template>
  <div>
    <el-button type="text" @click="connInfoFormDialogVisiable = true">新建连接</el-button>
    <el-table
        ref="tableList"
        :data="dbConnInfoList"
        border
        style="width: 800px"
        @selection-change="handleTableSelection">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column
          prop="connectionName"
          width="200px"
          label="连接名称"></el-table-column>
      <el-table-column
          prop="ip"
          label="IP"></el-table-column>
      <el-table-column prop="port" label="端口"></el-table-column>
      <el-table-column
          prop="dbTypeName"
          width="200px"
          label="数据库类型"></el-table-column>
      <el-table-column prop="databaseName" label="数据库名"></el-table-column>
    </el-table>

    <!-- 连接信息表单 -->
    <el-dialog title="新建连接" :visible.sync="connInfoFormDialogVisiable">
      <el-form size="mini"
               label-width="160px"
               label-position="right"
               :model="connInfo">
        <el-row>
          <el-col :span="12">
            <el-form-item prop="connectionName" label="连接名称">
              <el-input v-model="connInfo.connectionName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="ip" label="数据库类型">
              <el-select v-model="connInfo.dbType" clearable placeholder="请选择"
                         filterable allow-create default-first-option>
                <el-option v-for="(item, index) in supportedDbTypes"
                           :key="index"
                           :label="item"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="ip" label="IP">
              <el-select v-model="connInfo.ip" clearable placeholder="请选择"
                         filterable allow-create default-first-option>
                <el-option v-for="(item, index) in historyIpList"
                           :key="index"
                           :label="item"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="port"
                          label="端口">
              <el-input v-model="connInfo.port"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="username" label="用户名">
              <el-input v-model="connInfo.username"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="password"
                          label="密码">
              <el-input v-model="connInfo.password"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="databaseName" label="数据库">
              <el-input v-model="connInfo.databaseName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="encoding"
                          label="编码">
              <el-select v-model="connInfo.encoding" clearable placeholder="请选择"
                         filterable allow-create default-first-option>
                <el-option v-for="(item, index) in supportedEncodings"
                           :key="index"
                           :label="item"
                           :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="testConnection">测试连接</el-button>
        <el-button @click="connInfoFormDialogVisiable = false">取 消</el-button>
        <el-button type="primary" @click="connInfoFormDialogVisiable = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      formLabelWidth: 'auto',
      connInfoFormDialogVisiable: false, // 弹窗显示状态
      connInfo: {
        connectionName: undefined,
        dbType: 'MySQL5',
        ip: undefined,
        port: 3306,
        username: 'root',
        password: undefined,
        encoding: undefined,
        databaseName: undefined
      },
      historyIpList: [
        '192.168.129.62', "localhost"
      ],
      supportedEncodings: [
        'utf8', 'utf8mb4'
      ],
      supportedDbTypes: [
        'MySQL5', "Oracle"
      ],
      searchKey: "",
      showGenSettingDialog: false,
      tables: [],
      choosedTables: [],
      genSetting: {
        author: "",
        choosedOutputFiles: [],
        override: false,
        moduleName: "",
        choosedControllerMethods: [],
        rootPath: "",
      },
      userConfig: {},
      outputFileInfos: [],
      isNewProject: false,
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
    dbConnInfoList: function () {
      return [{
        port: 3306,
        ip: 'localhost',
        connectionName: 'localhost:3306',
        databaseName: "ruoyi",
        dbTypeName: "MySQL5"
      }];
    }
  },
  mounted: function () {

  },
  methods: {
    handleTableSelection(val) {
      this.choosedTables = val.map((t) => t.name);
    },
    loadAllConnectionInfo() {
      axios.get("/api/db/tables").then((res) => {
        this.tables = res;
      });
    },
    openGenSetting() {

    },
    testConnection() {
      let result = this.$api.saveConnectionInfo(this.connInfo);
      console.log(result)
    },
    openImportProjectView() {
      this.showSavedProjectsDialog = true;
      axios.get("/api/output-file-info/all-saved-project").then((res) => {
        this.savedProjects = res;
      });
    }
  },
}
</script>

<style scoped>

</style>