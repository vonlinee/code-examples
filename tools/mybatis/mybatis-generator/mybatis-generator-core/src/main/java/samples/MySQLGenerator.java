package samples;

import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLGenerator {

    public static void main(String[] args) throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        // 如果已经存在生成过的文件是否进行覆盖
        boolean overwrite = true;
        // File configFile = new File("ClassPath路径/generator-configuration.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        InputStream is = MySQLGenerator.class
                .getClassLoader()
                .getResourceAsStream("generateConfig-mysql5.xml");
        // 解析配置文件，得到配置对象Configuration
        Configuration config = cp.parseConfiguration(is);
        //
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 生成器
        MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
        // 开始生成
        generator.generate(null);
        List<IntrospectedTable> tables = config.getContexts().get(0).getIntrospectedTables();
        for (IntrospectedTable table : tables) {
            table.getAttribute("");
        }

    }
}
