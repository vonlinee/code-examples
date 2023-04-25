package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class CRUDTest {

    public static void main(String[] args) throws IOException {
        // 1.指定MyBatis主配置文件位置
        String resource = "mybatis-config.xml";
        // 2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        Configuration configuration = factory.getConfiguration();

        MappedStatement ms = configuration.getMappedStatement("mybatis.mapper.DepartmentMapper.selectList");

        System.out.println(ms);


        SqlSource sqlSource = ms.getSqlSource();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name1", "111");


        DynamicContextVisitor visitor = new DynamicContextVisitor(configuration, map);

        if (sqlSource instanceof DynamicSqlSource) {
            DynamicSqlSource dss = (DynamicSqlSource) sqlSource;

        }



        BoundSql boundSql = ms.getBoundSql(map);

        System.out.println(boundSql.getSql());
    }
}