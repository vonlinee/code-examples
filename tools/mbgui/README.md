## mybatis-plus-generator-ui

提供交互式的Web UI用于生成兼容mybatis-plus框架的相关功能代码，包括Entity,Mapper,Mapper.xml,Service,Controller等
，可以自定义模板以及各类输出参数，也可通过SQL查询语句直接生成代码。



[davidfantasy/mybatis-plus-generator-ui: 对mybatis-plus-generator进行封装，通过Web UI快速生成兼容Spring boot，mybatis-plus框架的各类业务代码 (github.com)](https://github.com/davidfantasy/mybatis-plus-generator-ui)



直接访问后端的地址不会看到变化



## 使用方法

1. 引入maven的相关依赖，注意scope只需要写test就可以了

```xml

<dependency>
    <groupId>com.github.davidfantasy</groupId>
    <artifactId>mybatis-plus-generator-ui</artifactId>
    <version>2.0.1</version>
    <scope>test</scope>
</dependency>
```

2. 在项目的test目录新建一个启动类，代码示例如下：

```java
public class GeberatorUIServer {

    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql://192.168.1.211:3306/example")
                .userName("root")
                .password("root")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                //数据库schema，MSSQL,PGSQL,ORACLE,DB2类型的数据库需要指定
                .schemaName("myBusiness")
                //如果需要修改entity及其属性的命名规则，以及自定义各类生成文件的命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法，详细可查看该接口的说明：                
                .nameConverter(new NameConverter() {
                    /**
                     * 自定义Service类文件的名称规则
                     */
                    @Override
                    public String serviceNameConvert(String tableName) {
                        return this.entityNameConvert(tableName) + "Service";
                    }

                    /**
                     * 自定义Controller类文件的名称规则
                     */
                    @Override
                    public String controllerNameConvert(String tableName) {
                        return this.entityNameConvert(tableName) + "Action";
                    }
                })
                //所有生成的java文件的父包名，后续也可单独在界面上设置
                .basePackage("com.github.davidfantasy.mybatisplustools.example")
                .port(8068)
                .build();
        MybatisPlusToolsApplication.run(config);
    }

}
```

**GeneratorConfig**还包含一些基本的配置参数以及各个可扩展的接口，比如自定义模板参数，具体的说明可查看源码注释。

3. 运行该启动类，启动一个Generator Server。然后访问[http://localhost:8068](http://localhost:8068/)（端口是可配置的）即可进入到管理界面。

#### 重要更新

**1.4.0** 版本之后，可支持将GeberatorUIServer独立部署为一个单独的spring boot项目，通过页面指定目标项目根目录的方式为多个项目提供源码生成服务。

**2.0.0** 版本已经支持最新的mybatis-plus 3.5.X，才用重构后新的MBP作为代码生成引擎。

## 主要功能

1. **数据表的查询和浏览**：可以直接浏览和查询配置的数据源的数据表信息，可选择一个或多个生成模板代码：

![数据表查询](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/table-list.png)

2. **输出文件的配置**
   ：内置Entity,Mapper,Service,Controller等6种类型代码的模板配置，可以上传模板进行替换，并修改各类参数，配置参数已经按照影响的文件类型重新进行了分类，并加入了部分文本说明；也可以自行添加其它类型的自定义输出文件。所有的配置项都会按照项目包名进行保存，只需一次性设置就可以了。

![输出文件类型配置](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/output-config.png)

![文件输出选项配置](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/strategy.png)

3. **代码生成选项**：将每次生成代码时可能变动的内容加入到代码生成选项中，方便调整每次的生成策略，比如：是否覆盖原文件，生成文件的种类等等：

![代码生成选项](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/generator-options.png)

4. **SQL结果集自动生成代码**：通过输入查询SQL，可自动在Mapper（Xml及Java）中生成对应的查询方法，DTO对象和ResultMap（结果集映射配置）

![SQL编辑](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/SQL-edit.png)

![SQL代码生成选项](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/SQL-generator-options.png)

5. **动态SQL增强**：自动识别含有mybatis动态参数的where条件，转换为mybatis的动态SQL条件

![动态SQL增强](https://gitee.com/davidfantasy/mybatis-plus-generator-ui/raw/master/imgs/dynamicsql.png)

## 常见问题

**Q:下载源码中怎么直接运行？**

**不建议直接下载源码运行**
，该项目是设计为直接嵌入到对应的业务项目中使用，可以自动识别项目路径等环境变量。如需采用源码运行，需要另行编译src\frontend中的静态资源（源码中不包含已编译的页面），在src\frontend文件夹中执行：

~~~shell
yarn install
yarn run build
~~~

然后修改src\test\java\TestApplication中数据库的相关配置，并运行。

**Q:支持哪些类型的数据库？**

支持几乎所有主流的数据库，具体可参考mybatis-plus-generator框架的文档。需要自行引入数据库的driver包，并在
GeneratorConfig中指定driverClassName。

**Q:怎么自定义模板参数？**

在GeneratorConfig中自定义TemplateVaribleInjecter，返回需要在模板中使用的参数，例如：

```java
 GeneratorConfig config=GeneratorConfig.builder()
        .templateVaribleInjecter(new TemplateVaribleInjecter(){
@Override
public Map<String, Object> getCustomTemplateVaribles(TableInfo tableInfo){
        Map<String, Object> params=new HashMap<>();
        return params;
        }
        })
```

**Q:保存的配置是存储到什么地方的？**

所有的用户保存的配置是按照basePackage分组保存到user.home目录的.mybatis-plus-generator-ui中的，不同项目的配置不会互相影响。

**Q:启动报错问题排查**

大部分的启动问题都是由于依赖冲突导致的，因为mybatis-plus-generator-ui也依赖于springboot和mybatis-plus，请先检查依赖包版本是否相差过大；1.X的版本仅支持最高mp的3.4.X版本，在高版本的springboot(大于2.4)上启动也会有问题，2.0.0版本已经解决了相关的兼容性问题；

















如何实现直接嵌入到对应的业务项目中使用，可以自动识别项目路径等环境变量。





nuxt.js生成



npm run generate



服务端渲染





run build时，Cannot find module '@babel/preset-env/lib/utils'

nuxt.js项目 ,npm run dev时出现Cannot find module ‘@babel/preset-env/lib/utils‘ 错误

https://blog.csdn.net/weixin_61922192/article/details/125604820

https://stackoverflow.com/questions/66325582/nuxt-js-cannot-find-module-babel-preset-env-lib-utils



```js
npm uninstall @babel/preset-env
npm install @babel/preset-env@7.12.17
```

如果安装@babel/preset-env@7.12.17时报错：Error: Cannot find module 'sshpk' 

sshpk是将rsa公钥转为OpenSSH格式



node-sass安装报错

gyp verb check python checking for Python executable "python2" in the PATH

gyp verb check python checking for Python executable "python" in the PATH

解决办法：

主要是windows平台缺少编译环境，

1、先运行： npm install -g node-gyp

2、然后运行：运行 npm install --global --production windows-build-tools 可以自动安装跨平台的编译器：gym



安装windows-build-tools需要管理员权限，需要赋予当前shell管理员权限



node-gyp

[安装node-gyp - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/164543031)



版本不兼容问题

Error: Node Sass version 8.0.0 is incompatible with ^4.0.0.



[sass-loader | webpack 中文文档 (docschina.org)](https://webpack.docschina.org/loaders/sass-loader/)

安装sass loader



升级到7.0.0版本npm install成功

```
"node-sass": "^7.0.0",
```









Error in parsing SVG: Non-whitespace before first tag.



报错

Error: Node Sass version 7.0.3 is incompatible with ^4.0.0.































sass-loader

一、sass-loader的作用和处理流程：

（1）作用：帮助webpack打包scss文件；

（2）处理流程：scss-loader是将scss格式的样式，先帮我们转换成css格式的样式，再交给css-loader根据各个文件的依赖关系整合成一段css，最后再交给style-loader帮我们挂载到页面的header部分；

二、安装：使用sass-loader需要安装两个依赖：sass-loader和node-sass

注意： node-sass可能由于翻墙的 原因安装失败，我们可以使用cnpm安装

三、sass-loader的配置：

由于sass-loader始终都是帮我们处理样式文件的，只不过是将scss语法的样式转换成css语法的样式，因此，sass-loader是和样式处理loader在一起的；





Module build failed (from ./node_modules/sass-loader/dist/cjs.js):
TypeError: this.getOptions is not a function





查看 node 版本和 node-sass 版本依赖关系

| NodeJS  | Supported node-sass version | Node Module |
| ------- | --------------------------- | ----------- |
| Node 19 | 8.0+                        | 111         |
| Node 18 | 8.0+                        | 108         |
| Node 17 | 7.0+, <8.0                  | 102         |
| Node 16 | 6.0+                        | 93          |
| Node 15 | 5.0+, <7.0                  | 88          |
| Node 14 | 4.14+                       | 83          |
| Node 13 | 4.13+, <5.0                 | 79          |
| Node 12 | 4.12+, <8.0                 | 72          |
| Node 11 | 4.10+, <5.0                 | 67          |
| Node 10 | 4.9+, <6.0                  | 64          |
| Node 8  | 4.5.3+, <5.0                | 57          |
| Node <8 | <5.0                        | <57         |





最终版本

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
    "js-base64": "^2.5.2",
    "lodash": "^4.17.11",
    "moment": "^2.24.0",
    "nuxt": "~2.11.0",
    "vue-codemirror": "^4.0.6",
    "vue-property-decorator": "^8.3.0"
  },
  "devDependencies": {
    "@nuxt/types": "^0.6.4",
    "babel-plugin-component": "^1.1.1",
    "colors": "^1.3.3",
    "cross-env": "^5.2.0",
    "eslint-config-prettier": "^3.1.0",
    "eslint-plugin-prettier": "2.6.2",
    "http-proxy-middleware": "^0.19.0",
    "node-sass": "^7.0.0",
    "nodemon": "^1.11.0",
    "sass-loader": "^10.0.1"
  }
}
```

改动就是，改动了node-sass和sass-loader的版本

```js
"node-sass": "^7.0.0",
"sass-loader": "^10.0.1"
```









