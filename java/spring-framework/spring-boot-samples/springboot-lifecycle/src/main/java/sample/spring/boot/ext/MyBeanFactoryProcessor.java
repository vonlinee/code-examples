package sample.spring.boot.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 它是Spring对外提供的用来拓展Spring的接口，能够在Spring容器加载了所有bean的信息之后、bean实例化之前执行。
 * 他修改bean的定义属性；其中 PropertyResourceConfigurer就是 BeanFactoryPostProcessor的典型应用
 *
 * PropertyResourceConfigurer可以将Xml文件中的占位符替换成属性文件中相应key对应的value值。
 */
@Component
public class MyBeanFactoryProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Utils.printObject(beanFactory);
    }
}
