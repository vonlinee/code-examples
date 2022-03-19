package sample.spring.aop.xml.beforeandafter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AnnotationAwareAspectJAutoProxyCreator
 * InternalAutoProxyCreator
 * org.springframework.aop.config.internalAutoProxyCreator 创建AOP代理类
 * Advisor
 * CGLib - SpringCGLib
 */
public class Main {
    private static ApplicationContext context;

    public static void main(String[] args) {
    	String string = Main.class.getResource("spring-aop-config.xml").toExternalForm();
        context = new ClassPathXmlApplicationContext(string);
        
        Object bean = context.getBean("org.springframework.aop.config.internalAutoProxyCreator");
        
        System.out.println(bean);
        //org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
        System.out.println(bean.getClass());
        
        Performer performer = (Performer) context.getBean("performer");
        
        System.out.println(performer.getClass()); //Performer$$EnhancerBySpringCGLIB$$feaefc15
        
        // org.springframework.aop.aspectj.AspectJPointcutAdvisor
        performer.perform();
    }
}
