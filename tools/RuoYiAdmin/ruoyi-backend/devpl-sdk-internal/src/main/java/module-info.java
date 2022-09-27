/**
 * 模块化使用：https://blog.csdn.net/weixin_34247032/article/details/88883304
 * https://juejin.cn/post/6847902216590721031
 */
open module devpl.sdk.internal {

    requires java.base;
    requires java.logging;
    requires java.net.http;
    requires java.sql;
    requires java.se;
    requires lombok;
    requires jsr305;

    requires hutool.all;

    // 表示允许访问指定包的public成员(编译及运行时)
    // 如果反射不直接通过类名调用，只是运行时通过包名使用，则只需open或opens即可
    // 如果是通过类名来反射，由于用到了该类，需要通过exports指定可以访问，不指定则编译期立即报错
    // 如果是通过类名来反射使用public方法或newInstance，如果没有exports，则运行时报错
    // 如果有exports，但是没有open，因此编译通过运行时报错
    exports io.devpl.sdk;
    exports io.devpl.sdk.beans;

    // 表示允许运行时通过反射使用
    // open的作用是表示该模块下的所有的包在runtime都允许deep reflection( 包括public及private类型);
    // opens package的作用只是允许该包在runtime都允许deep reflection
    // open及opens都仅仅是开放runtime时期的可以通过反射访问(蕴含了运行时的exports)。
    // opens不能在open module里使用
    // opens io.devpl.sdk;
}


