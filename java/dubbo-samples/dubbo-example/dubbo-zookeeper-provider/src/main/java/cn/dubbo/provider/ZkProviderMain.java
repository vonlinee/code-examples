package cn.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Brave
 * @create 2021-08-13 9:40
 * @description
 **/
public class ZkProviderMain {

    public static void main(String[] args) throws IOException {
        String spring_xml = "provider.xml";
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(spring_xml);
        context.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            System.out.println(next);
            if ("exit".equals(next)) {
                break;
            }
        }
    }

}
