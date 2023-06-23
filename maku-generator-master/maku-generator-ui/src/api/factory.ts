import service from '@/utils/request'

import http from '@/utils/http'

// 获取文件目录树形结构
export function apiGetFileTree(root_path: string) {
    return service.get('/factory/generator/file-tree', {
        params: {
            rootPath: root_path
        }
    });
}

// 获取文件内容
export function apiGetFileContent(path: string) {
    console.log(path)
    return service.get('/factory/generator/file', {
        params: {
            path: path
        }
    });
}

export function test() {
    http.get("/factory/generator/file")
}