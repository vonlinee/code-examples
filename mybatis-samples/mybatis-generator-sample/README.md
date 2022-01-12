一般配置了 includeCompileDependencies 后就不需要配置其他依赖了，因为 includeCompileDependencies 会将当前 pom 的 dependencies 中所以 Compile 期的依赖全部添加到生成器的类路径中



但有的人不想配置 **includeCompileDependencies** ，或者想在MyBatis Generator插件中使用另一个版本的依赖，就可以配置 **dependencies**

另外，我看到网上大部分文章都会配置 **mybatis-generator-core** 这个依赖，但是 MyBatis Generator 官网的案例中都没有提到说要配置这个依赖，我没有配置，并且可以正常使用 MyBatis Generator

# MyBatis Generator 配置

教程：https://juejin.cn/post/6844903982582743048

https://blog.csdn.net/qq_33326449/article/details/105930655

MyBatis Generator 插件启动后，会根据你在 pom 中配置都路径找到该配置文件

这个配置文件才是详细都配置 MyBatis Generator 生成代码的各种细节



# 使用

官网：http://mybatis.org/generator/index.html



The content of element type "context" must match "(property*,plugin*,commentGenerator?,(connectionFactory|jdbcConnection),javaTypeResolver?,javaModelGenerator,sqlMapGenerator?,javaClientGenerator?,table+)".


未完待续============

