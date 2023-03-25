import qs from 'qs'; // 根据需求是否导入qs模块
import {get, post, postJson} from './request.js'
import Vue from 'vue'

const api = {
    get(params) {
        return get('', {
            url: '/artical',
            method: 'get',
            params,
            hideloading: false //设置不隐藏加载loading
        })
    },
    getTableList(id, params) {
        return post('', {
            url: '/detail',
            method: 'get',
            params: {
                id,
                params
            },
            hideloading: true
        })
    },
    login(data) {
        return post('', {
            url: '/adduser',
            method: 'post',
            data: qs.stringify(data), //注意post提交用data参数
            hideloading: true

        })
    },
    saveConnectionInfo(connInfo) {
        return post("/api/db/conn/save", connInfo)
    },
    saveOutputFileInfo(val) {
        return postJson("/api/output-file-info/save", val)
    },
    downloadTemplate() {

    },
    startCodeGeneration(param) {
        return postJson("/api/mbpg/codegen", param)
    },
    getDatabaseNames(param) {
        return get("/api/db/conn/dbnames", {connectionName: param})
    },
    getConnectionNames() {
        return get("/api/db/conn/info/names", null)
    },
    getDatabaseTables(connName, dbName) {
        return get("/api/db/tables", {
            connName: connName,
            dbName: dbName
        })
    },
    getUserConfig() {
        return get("/api/output-file-info/user-config")
    }
}
// 挂载到Vue全局实例上
Vue.prototype.$api = api

export default api