package sample.spring.aop.xml.around;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sample.spring.aop.xml.afterreturning.Performer;

/**
 * InternalAutoProxyCreator
 * org.springframework.aop.config.internalAutoProxyCreator 创建AOP代理类
 */
public class Main {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("abc.xml");
        Performer performer = (Performer) context.getBean("performer");
        
        
        
//        performer.perform();
    }
}
