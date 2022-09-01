
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


上下文准备阶段



BeanDefinitionLoader

BeanNameGenerator
ResourceLoader

BeanDefinitionLoader.load(Object)
```java
private int load(Object source) {
    // source即为启动类
    Assert.notNull(source, "Source must not be null");
    if (source instanceof Class<?>) {
        return load((Class<?>) source);
    }
    if (source instanceof Resource) {
        return load((Resource) source);
    }
    if (source instanceof Package) {
        return load((Package) source);
    }
    if (source instanceof CharSequence) {
        return load((CharSequence) source);
    }
    throw new IllegalArgumentException("Invalid source type " + source.getClass());
}
```

```java
private int load(Class<?> source) {
    // Groovy判断
    if (isGroovyPresent() && GroovyBeanDefinitionSource.class.isAssignableFrom(source)) {
        // Any GroovyLoaders added in beans{} DSL can contribute beans here
        GroovyBeanDefinitionSource loader = BeanUtils.instantiateClass(source, GroovyBeanDefinitionSource.class);
        load(loader);
    }
    if (isEligible(source)) {
        // AnnotatedBeanDefinitionReader
        this.annotatedReader.register(source);
        return 1;
    }
    return 0;
}
```

org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh
```java
@Override
public final void refresh() throws BeansException, IllegalStateException {
    try {
        super.refresh();
    } catch (RuntimeException ex) {
        WebServer webServer = this.webServer;
        if (webServer != null) {
            webServer.stop();
        }
        throw ex;
    }
}
```

调用其父类的refresh
org.springframework.context.support.AbstractApplicationContext.refresh

```java
@Override
public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        // Prepare this context for refreshing.
        prepareRefresh(); // 子类有自己的实现

        // Tell the subclass to refresh the internal bean factory.
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // Prepare the bean factory for use in this context.
        prepareBeanFactory(beanFactory);

        try {
            // Allows post-processing of the bean factory in context subclasses.
            postProcessBeanFactory(beanFactory);

            // Invoke factory processors registered as beans in the context.
            // 这一步会注册所有的bean
            invokeBeanFactoryPostProcessors(beanFactory);

            // Register bean processors that intercept bean creation.
            registerBeanPostProcessors(beanFactory);

            // Initialize message source for this context.
            initMessageSource();

            // Initialize event multicaster for this context.
            initApplicationEventMulticaster();

            // Initialize other special beans in specific context subclasses.
            // 初始化其他在@Configuration中的的bean
            onRefresh();

            // Check for listener beans and register them.
            registerListeners();
            
            // 初始化剩下的所有的非lazy初始化的单例对象
            // Instantiate all remaining (non-lazy-init) singletons.
            finishBeanFactoryInitialization(beanFactory);

            // Last step: publish corresponding event.
            // 发布事件通知
            finishRefresh();
        }

        catch (BeansException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Exception encountered during context initialization - " +
                        "cancelling refresh attempt: " + ex);
            }

            // Destroy already created singletons to avoid dangling resources.
            destroyBeans();

            // Reset 'active' flag.
            cancelRefresh(ex);

            // Propagate exception to caller.
            throw ex;
        }

        finally {
            // Reset common introspection caches in Spring's core, since we
            // might not ever need metadata for singleton beans anymore...
            resetCommonCaches();
        }
    }
}
```

PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory, List<BeanFactoryPostProcessor>)




org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry









