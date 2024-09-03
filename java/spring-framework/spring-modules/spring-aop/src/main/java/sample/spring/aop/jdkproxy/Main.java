package sample.spring.aop.jdkproxy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class Main {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public IUserService userService() {
        return new UserService();
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        UserService userService = context.getBean(UserService.class);
        userService.login("zs", "123456");
    }
}
