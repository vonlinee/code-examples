







https://www.jianshu.com/p/5b94d5f51e31



```groovy
plugins {
    //id 'java'
    //springboot插件，加入版本，那么Spring相关依赖，则自动加入(当使用其他插件的时候，还会自动加载插件所带的任务)
    id 'org.springframework.boot' version '2.1.6.RELEASE'
    //第一种引入方式：写在此处，需要手动设置依赖管理的版本，否则无法执行（手动指定版本，好处是插件集中管理在plugins里面）
	//id 'io.spring.dependency-management' version '1.0.8.RELEASE'
}
apply plugin: 'java'
//第二种引入方式：应用依赖管理插件，自动给插件追加版本号（建议使用此配置）
apply plugin: 'io.spring.dependency-management'

sourceCompatibility = 1.8

repositories {
    mavenLocal()
    maven{ url 'http://maven.aliyun.com/nexus/content/groups/public/'}
}
//设置commons-pool2版本为'2.6.1'，Spring依赖的是2.6.2
ext['commons-pool2.version'] = '2.6.1'
dependencies {
    //Spring模块
    compile("org.springframework.boot:spring-boot-starter-web")
    //Spring依赖的第三方模块(2.1.6依赖的是3.8.1)
    compile("org.apache.commons:commons-lang3")
    //Spring依赖的第三方模块(2.1.6依赖的是2.6.2)指定依赖版本为2.6.1
    compile('org.apache.commons:commons-pool2')
}
```



```plain
https://juejin.cn/post/6890549273105006600
```

















