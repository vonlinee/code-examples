package sample.spring.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class SpringUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SpringUtils.class);

    public static ApplicationContext loadXmlAppContext() {
        return new ClassPathXmlApplicationContext("spring-config.xml");
    }

    /**
     * 加载Spring XML 配置文件
     * @param clazz 运行的主类
     * @return context
     */
    public static ApplicationContext loadApplicationContext(Class<?> clazz) {
        String packageName = clazz.getPackage().getName().replace(".", "/");
        String launchDirectoryPath = new File("").getAbsolutePath() + "/src/main/java/" + packageName;
        File dir = new File(launchDirectoryPath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((dir1, name) -> dir1.getName().endsWith(".xml"));
            if (files == null || files.length == 0) {
                LOG.info(launchDirectoryPath + "下不存在XML文件!");
                return null;
            }
            for (File file : files) {
                if (file.getName().contains("spring")) {
                    dir = file;
                    break;
                }
            }
            LOG.info(launchDirectoryPath + "下不存在名字包含spring的XML文件!");
        }
        if (dir.isFile()) {
            return new ClassPathXmlApplicationContext(dir.getAbsolutePath());
        }
        return null;
    }
}
