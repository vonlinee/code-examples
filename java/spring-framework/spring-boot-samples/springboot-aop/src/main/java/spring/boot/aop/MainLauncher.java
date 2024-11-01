package spring.boot.aop;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.boot.aop.service.IUserService;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableAspectJAutoProxy(exposeProxy = true)
public class MainLauncher {
    public static void main(String[] args) {

        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\Temp");

        /**
         * {@link SpringApplication#run(String...)}
         * {@link CglibAopProxy#getProxy()}
         * {@link org.springframework.aop.framework.CglibAopProxy}
         * {@link org.springframework.beans.factory.support.AbstractBeanFactory#getBean(String)}
         * 不同环境下的Application不同，但都是 {@link org.springframework.context.ConfigurableApplicationContext}
         * {@link AnnotationConfigServletWebServerApplicationContext#refresh()}
         * {@link org.springframework.context.support.AbstractApplicationContext}
         * {@link org.springframework.context.support.AbstractRefreshableApplicationContext}
         */
        ConfigurableApplicationContext context = SpringApplication.run(MainLauncher.class, args);

        // System.out.println(beanFactory.getBeanDefinition("logAspect"));

        // UserService userService = beanFactory.getBean("userService", UserService.class);

        // CglibAopProxy proxy;
        //

        IUserService userService = context.getBean(IUserService.class);
        userService.login("zs", "123");


        ProxyFactory pf;

//        userService.login("zs", "123");
//        userService.login("ls", "456");

        // BeanA a = beanFactory.getBean(BeanA.class);
        // System.out.println(a);
        // BeanB b = beanFactory.getBean(BeanB.class);
        // System.out.println(b);
    }
}
