

工具类库


gradle version > 5.2

编辑 Help > Edit Custom VM Options 增加 -Dfile.encoding=UTF-8，增加后重启IDEA生效

一、设置IDEA的Gradle vm option
在file-> setting -> build -> build tools -> gradle -> Gradle VM Options中添加
-Dfile.encoding=utf-8


Sublime Text 3 插件开发
https://www.bilibili.com/video/BV1t54y1k7VH?p=2&spm_id_from=pageDriver

Gradle版本设置
1.gradle/gradle-wrapper.properties中指定的版本
2.Idea中gradle使用的版本：   use Gradle from
3.本地安装的版本
4.Gradle User Home的版本







org.springframework.jdbc.datasource.init.DataSourceInitializer


delegate













Idea链式调用方法对齐
https://www.jianshu.com/p/378ba1f4ddb4
找到设置项：Editor -> Code Style -> Java -> Wrapping And Braces -> Chain Method Call


在 Eclipse 的默认代码格式化环境中，链式调用代码会挤在一行，看起来很痛苦

在 Eclipse 中按照以下顺序打开代码格式化的配置项：
Windows → Preferences → Java → Code Style → Formatter


关键的配置项如下：
1、Maximum line width：120「一行最大宽度，120」（超过 120 就自动换行）
2、Function Calls → Qualified Invocations「方法调用 → xxxxx」
其中 line wrapping policy 「换行策略」选择：
wrap all elements, except first element if not necessary「第一个元素可以不换行，其他都换行」
并且勾选复选框 force split, even if line shorter than maximum line width「强制换行，即使该行没有达到最大换行的宽度」
这样设置后，Eclipse 就能够为链式调用的代码自动换行了
不过，这样的换行效果仍然不够理想，如果换行策略优化为：
wrap all elements, except second element if not necessary「前两个元素可以不换行，其他都换行」
这样就更好了。





gradle 5.6版本
Gradle
命令
查看Project中所有的Task：gradle tasks
查看Project中所有的properties：gradle properties
执行某个任务：gradlew 任务名


jaxb

