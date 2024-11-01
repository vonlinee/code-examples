package sample.spring.core.xml.aware.appcontext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// In most application scenarios, most beans in the container are singletons.
// When a singleton bean needs to collaborate with another singleton bean,
// or a non-singleton bean needs to collaborate with another non-singleton bean,
// you typically handle the dependency by defining one bean as a property of the other.
// A problem arises when the bean lifecycles are different.
// Suppose singleton bean A needs to use non-singleton (prototype) bean B,
// perhaps on each method invocation on A. The container only creates the singleton bean A once,
// and thus only gets one opportunity to set the properties.
// The container cannot provide bean A with a new instance of bean B every time one is needed.
// A solution is to forego some inversion of control.
// You can make bean A aware of the container by implementing the ApplicationContextAware interface,
// and by making a getBean("B") call to the container ask for (a typically new) bean B instance
// every time bean A needs it. The following is an example of this approach:
public class Test {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
        Shape shape = (Shape) context.getBean("triangle");
        shape.draw();
    }
}
