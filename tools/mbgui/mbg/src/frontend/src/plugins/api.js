import qs from 'qs'; // 根据需求是否导入qs模块
import {get, post} from './request.js'
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
    }
}

export default api
