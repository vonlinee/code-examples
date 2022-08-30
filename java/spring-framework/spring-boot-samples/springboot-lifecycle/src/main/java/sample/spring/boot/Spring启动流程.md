
SpringApplication.run(Xxx.class, args)
// 扫描各种组件
SpringApplication app = new SpringApplication(Xxx.class);
// 运行
app.run(args);

SpringApplication用于从Main方法启动一个Spring应用

1.创建一个合适的ApplicationContext实例
2.注册CommandLinePropertySource，将命令行参数放到Spring管理的properties中
3.刷新ApplicationContext, 加载所有的单例Bean
4.触发CommandLineRunner这类Bean的执行



```java
public ConfigurableApplicationContext run(String... args) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    ConfigurableApplicationContext context = null;
    configureHeadlessProperty();
    // 获取所有的SpringApplicationRunListener实例
    SpringApplicationRunListeners listeners = getRunListeners(args);
    listeners.starting();
    try {
        // 解析命令行参数
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        // 创建 Environment 实例
        ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
        configureIgnoreBeanInfo(environment);
        Banner printedBanner = printBanner(environment);
        // 创建阶段 ApplicationContext
        context = createApplicationContext();
        // 准备阶段 ApplicationContext
        prepareContext(context, environment, listeners, applicationArguments, printedBanner);
        // 刷新阶段 ApplicationContext
        refreshContext(context);
        // 刷新阶段完成后的后置处理 ApplicationContext
        afterRefresh(context, applicationArguments);
        stopWatch.stop();
        if (this.logStartupInfo) {
            new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
        }
        listeners.started(context);
        callRunners(context, applicationArguments);
    }
    catch (Throwable ex) {
        handleRunFailure(context, ex, listeners);
        throw new IllegalStateException(ex);
    }
    try {
        listeners.running(context);
    }
    catch (Throwable ex) {
        handleRunFailure(context, ex, null);
        throw new IllegalStateException(ex);
    }
    return context;
}
```




@PropertySource是在refreshContext阶段才进行配置的


AnnotationConfigServletWebServerApplicationContext


public AnnotationConfigServletWebServerApplicationContext() {
    this.reader = new AnnotatedBeanDefinitionReader(this);
    this.scanner = new ClassPathBeanDefinitionScanner(this);
}



















