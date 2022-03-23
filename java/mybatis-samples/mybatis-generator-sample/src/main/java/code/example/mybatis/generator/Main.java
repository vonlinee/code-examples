package code.example.mybatis.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * MyBatis Generator 是 MyBatis 提供的一个代码生成工具。
 * 可以帮我们生成 表对应的持久化对象 (po)、操作数据库的接口 (dao)、CRUD sql的xml (mapper)。
 * MyBatis Generator 是一个独立工具，你可以下载它的jar包来运行、也可以在 Ant 和 maven 运行。
 * <p>
 * 		1.https://juejin.cn/post/6844903982582743048
 * 		2.https://blog.csdn.net/qq_33326449/article/details/105930655
 * <p>
 * 官网：http://mybatis.org/generator/index.html
 * @author someone
 */
public class Main {
    // 可以通过Maven插件的方式启动，也可以通过代码启动
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        File configFile = new File("mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        //org.mybatis.generator.config.Configuration
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
