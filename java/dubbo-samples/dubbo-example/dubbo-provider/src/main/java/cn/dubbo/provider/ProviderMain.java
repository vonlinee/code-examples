package cn.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderMain {

    public static void main(String[] args) {
        String spring_xml = "provider.xml";
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(spring_xml);
        context.start();
    }
}
