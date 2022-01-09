package cn.dubbo.consumer;

import cn.dubbo.consumer.service.IProviderService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Brave
 * @create 2021-08-13 10:00
 * @description
 **/
public class ConsumerMain {

    public static void main(String[] args) throws IOException {
        String spring_xml = "consumer.xml";
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(spring_xml);
        IProviderService providerService = (IProviderService) context.getBean("providerService");
        String response = providerService.sayHello("AAA");
        System.out.println(response);
    }
}
