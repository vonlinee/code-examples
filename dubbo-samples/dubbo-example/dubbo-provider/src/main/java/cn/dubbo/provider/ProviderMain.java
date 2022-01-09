package cn.dubbo.provider;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Brave
 * @create 2021-08-13 9:40
 * @description
 **/
public class ProviderMain{

    public static void main(String[] args) {
        String spring_xml = "provider.xml";
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(spring_xml);
        context.start();
    }

}
