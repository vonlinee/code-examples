package code.example.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import code.example.mybatis.mapper.EntityMapper;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        String resource = "mybatis-mapper.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        
//        List<Map<String, Object>> list = session.selectList("queryAll");
//        for (Map<String, Object> map : list) {
//            System.out.println(map);
//        }
        
        // org.apache.ibatis.session.defaults.DefaultSqlSession@2d3379b4
        
        EntityMapper mapper = session.getMapper(EntityMapper.class);
       
        mapper.queryAll();
        
        session.commit();
    }
}
