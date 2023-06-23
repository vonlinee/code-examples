import {http as request} from "./axios";
import {AxiosPromise} from "axios";

/**
 * axios请求配置: https://www.axios-http.cn/docs/req_config
 */
interface config {
    // method: any,
    // url: string,
    // headers?: object,
    // params?: object | string,
    // responseType?: string,
    // data?: object | string,
    // timeout?: number,
    [key: string]: any
}

/**
 * 定义常用的请求方法
 */
const http = {
    getBody(url: string, params?: any, headers: { "Content-Type": string } = {"Content-Type": "application/json"}) {
        const config: config = {
            method: "get",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    /**
     * get请求
     * @param url
     * @param params
     */
    get(url: string, params?: any): AxiosPromise {
        const config: config = {
            method: "get",
            url: url,
        };
        if (params) config.params = params;
        return request(config);
    },
    blob(url: string, params?: any) {
        const config: config = {
            method: "get",
            url: url,
            responseType: "blob",
        };
        if (params) config.params = params;
        return request(config);
    },
    post(url: string, params?: any, headers: { "Content-Type": string } = {"Content-Type": "application/json"}) {
        const config: config = {
            method: "post",
            url: url,
            headers,
        };
        if (params) {
            config.data = params;
        }
        return request(config);
    },
    postWWW(url: string, params?: any, headers = {"Content-Type": "application/x-www-form-urlencoded"}) {
        const config: config = {
            method: "post",
            url: url,
            headers,
        };
        if (params) config.params = params;
        return request(config);
    },
    put(url: string, params?: any, headers = {"Content-Type": "application/json"}) {
        const config: config = {
            method: "put",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    putWWW(url: string, params?: any, headers = {"Content-Type": "application/x-www-form-urlencoded"}) {
        const config: config = {
            method: "put",
            url: url,
            headers,
        };
        if (params) config.params = params;
        return request(config);
    },
    putQuery(url: string, params?: any) {
        const config: config = {
            method: "put",
            url: url,
        };
        if (params) config.params = params;
        return request(config);
    },
    delete(url: string, params?: any, headers = {"Content-Type": "application/json"}) {
        const config: config = {
            method: "delete",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    deleteQuery(url: string, params?: any) {
        const config: config = {
            method: "delete",
            url: url,
        };
        if (params) config.params = params;
        return request(config);
    },
    /**
     * 表单提交 multipart/form-data
     * @param url
     * @param params
     * @param headers
     */
    postForm(url: string, params?: any, headers = {"Content-Type": "multipart/form-data"}) {
        const config: config = {
            method: "post",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    postJson(url: string, params?: any, headers = {"Content-Type": "application/json"}) {
        const config: config = {
            method: "post",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    formGet(url: string, params?: any, headers = {"Content-Type": "multipart/form-data"}) {
        const config: config = {
            method: "get",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    formPut(url: string, params?: any, headers = {"Content-Type": "multipart/form-data"}) {
        const config: config = {
            method: "put",
            url: url,
            headers,
        };
        if (params) config.data = params;
        return request(config);
    },
    upload(url: string, params?: any, headers = {"Content-Type": "application/x-www-form-urlencoded"}, timeout = 1200000) {
        const config: config = {
            method: "post",
            url: url,
            headers,
            timeout
        };
        if (params) config.data = params;
        return request(config);
    },
};

export default http;