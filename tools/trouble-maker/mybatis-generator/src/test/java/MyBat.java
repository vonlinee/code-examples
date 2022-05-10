import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyBat {

    static {
        final SAXParserFactory factory = SAXParserFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl", ClassLoader.getSystemClassLoader());
    }

    public void test1() throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        // 如果已经存在生成过的文件是否进行覆盖
        boolean overwrite = true;
        // File configFile = new File("ClassPath路径/generator-configuration.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        InputStream is = this.getClass()
                             .getClassLoader()
                             .getResourceAsStream("C:\\Users\\vonline\\Desktop\\code-samples\\tools\\trouble-maker\\mybatis-generator\\src\\main\\resources\\mybatis\\generatorConfig.xml");
        Configuration config = cp.parseConfiguration(is);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
        generator.generate(null);
    }

    public static void main(String[] args) throws XMLParserException, SQLException, IOException, InterruptedException, InvalidConfigurationException {
        new MyBat().test1();
    }
}
