package code.example.springmvc.web;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;

@HandlesTypes({HelloService.class})
public class HelloServletContainerInitializer implements ServletContainerInitializer {
 
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("param class\t" + c);
        if (c != null) {
            for (Class<?> clazz : c ){
                System.out.println(clazz.getCanonicalName());
            }
        }
        ServletRegistration.Dynamic dynamic = ctx.addServlet("hello", new HelloServlet());
        dynamic.addMapping("/hello");
    }
}