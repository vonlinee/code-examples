package io.spring.boot.common.runner;

import io.spring.boot.dao.IScoreDao;
import io.spring.boot.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.spring.boot.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行
 * CommandLineRunner接口主要用于实现在应用初始化后，去执行一段代码块逻辑，这段初始化代码在整个应用生命周期内只会执行一次
 * 1.和@Component注解一起使用
 * 2.和@SpringBootApplication注解一起使用：继承了@SpringBootApplication的类同时实现CommandLineRunner接口，那么启动时就执行
 * 3.声明一个实现了CommandLineRunner接口的Bean：在SpringBootApplication里定义一个Bean，改Bean实现了CommandLineRunner接口
 * 注意：在实现CommandLineRunner接口时，run(String… args)方法内部如果抛异常的话，会直接导致应用启动失败，所以，一定要记得将危险的代码放在try-catch代码块里
 * 把该Bean放入容器即可
 *
 * 一个应用可能存在多个CommandLineRunner接口实现类，如果我们想设置它们的执行顺序，可以使用 @Order实现
 */
@Component
@Order(value = 2)
public class MyStartupRunner1 implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyStartupRunner1.class);

    @Autowired
    private IStudentService stuService;

    @Autowired
    private IScoreDao scoreDao;

    @Autowired
    private HelloWorldService helloWorldService;

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 11111111 &lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;");
        logger.info("测试这里可以访问数据库：" + stuService.getList().size() + "，" + scoreDao.getList().size());
        // // 指定数据源
        // logger.info("指定数据源Ds1 >>> " + stuService.getListByDs1().get(0).getName());
        // logger.info("指定数据源Ds2 >>> " + stuService.getListByDs2().get(0).getName());
        // logger.info("############" + this.helloWorldService.getHelloMessage());
    }
}


