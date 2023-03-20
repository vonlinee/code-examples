const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');

console.log("==========================")

module.exports = {
    lintOnSave: false,
    configureWebpack: {
        plugins: [
            new MonacoWebpackPlugin() // 配置JS在线代码编辑器monaco-editor的辅助文件
        ]
    }
};
