JDK11


下面简要介绍一下各个目录的作用：

`.nuxt/` ：用于存放 Nuxt.js 的核心库文件。例如，你可以在这个目录下找到 `server.js` 文件，描述了 Nuxt.js 进行服务端渲染的逻辑（参见下一段 “Nuxt.js 的渲染流程”）， `router.js` 文件包含一张自动生成的路由表。

`assets/` ：用于存放静态资源，该目录下的资源使用 Webpack 构建。

`components/` ：存放项目中的各种组件。注意，只有在这个目录下的文件才能被称为 **组件** 。

`layouts/` ：创建自定义的页面布局，可以在这个目录下创建全局页面的统一布局，或是错误页布局。如果需要在布局中渲染 `pages` 目录中的路由页面，需要在布局文件中加上 `<nuxt />` 标签。

`middleware/` ：放置自定义的中间件，会在加载组件之前调用。

`pages/` ：在这个目录下，Nuxt.js 会根据目录的结构生成 `vue-router` 路由，详见下文。

`plugins/` ：可以在这个目录中放置自定义插件，在根 `Vue` 对象实例化之前运行。例如，可以将项目中的埋点逻辑封装成一个插件，放置在这个目录中，并在 `nuxt.config.js` 中加载。

static/` ：不使用 Webpack 构建的静态资源，会映射到根路径下，如 `robots.txt

`store/` ：存放 Vuex 状态树。

`nuxt.config.js` ：Nuxt.js 的配置文件，详见下文





```js
// 查看 xxx 版本
格式：npm view xxx versions --json
举例：npm view monaco-editor versions --json
 
// monaco-editor 插件，必须
npm install monaco-editor --save-dev
// monaco-editor-webpack-plugin 插件，非必须
npm install monaco-editor-webpack-plugin --save-dev
// monaco-editor-nls 插件，非必须
npm install monaco-editor-nls --save-dev
// monaco-editor-esm-webpack-plugin 插件，非必须
npm install monaco-editor-esm-webpack-plugin --save-dev

// 引入 font-awesome 图标库，非必须
npm install font-awesome --save

yarn remove monaco-editor 


npm install monaco-editor@0.29.1 --save-dev

npm view monaco-editor versions
npm view less-loader versions
```



全部删除

npm remove monaco-editor monaco-editor-webpack-plugin monaco-editor-nls monaco-editor-esm-webpack-plugin



npm list monaco-editor





1. npm install -g npm-remote-ls

npm-remote-ls bower



npm i @babel/preset-env@7.12.17



yarn info monaco-editor@0.34.0

yarn info monaco-editor@0.35.0

依赖的webpack: '^5.74.0'



yarn add monaco-editor@0.35.1



yarn remove monaco-editor@0.35.1





yarn add monaco-editor@0.34.0





npm view monaco-editor-nls versions

```json
[
  '0.0.1', '0.0.2',
  '1.0.0', '1.0.1',
  '1.0.2', '1.0.4',
  '2.0.0', '3.0.0'
]
```

yarn info monaco-editor-nls@3.0.0

yarn add monaco-editor-nls@3.0.0





问题1：Module build failed (from ./node_modules/less-loader/dist/cjs.js):                            friendly-errors 11:36:52
Error: Cannot find module 'less'

pacckage-info.json中安装的less-loader版本是^7.0.0

```
"less-loader": "^7.0.0",
```

因此yarn info less-loader@^7.0.0，从结果知道：依赖的less版本为^3.12.2

yarn add less@3.12.2



问题2：

ERROR  in ./node_modules/monaco-editor/esm/vs/language/css/monaco.contribution.js            friendly-errors 11:40:19

Module parse failed: Unexpected token (30:15)                                                 friendly-errors 11:40:19
You may need an appropriate loader to handle this file type, currently no loaders are configured to process this file. See https://webpack.js.org/concepts#loaders

原因就是webpack不能处理monaco.contribution.js这个文件类型，实际上就是个js文件，可能因为monaco.contribution.js这个文件名包含了多个点字符



尝试安装monaco-editor-webpack-plugin



yarn info monaco-editor-webpack-plugin

yarn info monaco-editor-webpack-plugin@6.0.0

npm view monaco-editor-webpack-plugin versions

注意版本冲突，项目使用的webpack版本是4.46

```json
  devDependencies: {
    'css-loader': '^6.5.1',
    'file-loader': '^6.2.0',
    glob: '^7.2.0',
    'monaco-editor': '^0.31.0',
    'style-loader': '^3.3.1',
    typescript: '^4.4.4',
    webpack: '^5.64.1',
    'webpack-cli': '^4.9.1'
  },
```



最后确定版本：yarn add monaco-editor-webpack-plugin@1.0.0





在页面中使用

在HTML中创建一个容器，然后使用下面的代码进行初始化就可以得到一个在线编辑器了，但是现在这个编辑器也只是编辑器，什么辅助功能都没有，比如代码提示、右键菜单等等。

```js

import * as monaco from 'monaco-editor/esm/vs/editor/editor.api.js';
const monacoInstance=monaco.editor.create(document.getElementById("monaco"),{
value:`console.log("hello,world")`,

language:"javascript"

})
```

开启辅助功能

如何开启辅助功能，一种是我们根据自己的需要把辅助的文件引入到页面中，方法参考https://zhuanlan.zhihu.com/p/47746336，另外一种就是用webpack来实现。

安装monaco-editor-webpack-plugin这个模块。

在webpack.config.js中进行配置，MonacoWebpackPlugin可以接受language和features来配置辅助功能，具体配置参数可以查看npm官网即可。





npm install --save-dev monaco-editor-webpack-plugin








运行nuxt 报Cannot find module ‘@babel/preset-env/lib/utils‘

Module build failed (from ./node_modules/babel-loader/lib/index.js):                          friendly-errors 19:49:22
Error: C:\Users\Von\Downloads\mbgui\mbg\src\frontend\.nuxt\client.js: Cannot find module '@babel/preset-env/lib/utils'

参考github issue：https://github.com/microsoft/monaco-editor/issues/2903





nuxt版本依赖webpack

yarn info nuxt@2.11.0

npm view nuxt@2.11.0

npm view nuxt versions



...\node_modules\@nuxt\webpack\node_modules\webpack\lib\RuleSet.js:167

Error: Rule can only have one resource source (provided resource and test + include + exclude)

原因在于webpack版本冲突





[在vue中使用Monaco-editor的项目总结 - 掘金 (juejin.cn)](https://juejin.cn/post/7198434484236075045)









```json
{
  "name": "code-generator-ui",
  "version": "1.0.0",
  "description": "mybatis-plus代码生成器UI端",
  "author": "david",
  "scripts": {
    "dev": "node server.js",
    "build": "node build.js",
    "lint": "eslint --ext .js,.vue --ignore-path .gitignore ."
  },
  "dependencies": {
    "@babel/preset-env": "^7.12.17",
    "axios": "~0.21.1",
    "element-ui": "~2.13.0",
    "file-saver": "^2.0.2",
    "font-awesome": "^4.7.0",
    "js-base64": "^2.5.2",
    "lodash": "^4.17.11",
    "moment": "^2.24.0",
    "core-js": "2",
    "monaco-editor": "0.30.0",
    "monaco-editor-nls": "3.0.0",
    "nuxt": "~2.16.3",
    "qs": "^6.11.1",
    "vue": "2.7.14",
    "vue-codemirror": "^4.0.6",
    "vue-property-decorator": "^8.3.0",
    "vuex": "3.6.0"
  },
  "devDependencies": {
    "@nuxt/types": "^0.6.4",
    "babel-plugin-component": "^1.1.1",
    "colors": "^1.3.3",
    "cross-env": "^5.2.0",
    "eslint-config-prettier": "^3.1.0",
    "eslint-plugin-prettier": "2.6.2",
    "express": "^3.17.2",
    "http-proxy-middleware": "^0.19.0",
    "monaco-editor-esm-webpack-plugin": "^2.1.0",
    "monaco-editor-webpack-plugin": "6.0.0",
    "node-sass": "^7.0.0",
    "nodemon": "^1.11.0",
    "sass-loader": "^10.0.1",
    "less": "3.12.2",
    "less-loader": "^7.0.0"
  },
  "overrides": {
    "babel-plugin-lodash": {
      "@babel/types": "~7.20.0"
    }
  }
}
```








https://www.cnblogs.com/DarkCrow/p/17010850.html

































npm install monaco-editor-webpack-plugin@6.0.0





[javascript - Error: Rule can only have one resource source (provided resource and test + include + exclude) - Stack Overflow](https://stackoverflow.com/questions/64373393/error-rule-can-only-have-one-resource-source-provided-resource-and-test-incl)



nuxtjs自带webpack
