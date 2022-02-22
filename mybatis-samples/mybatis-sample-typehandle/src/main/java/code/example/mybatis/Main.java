package code.example.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import code.example.mybatis.mapper.EntityMapper;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        //1.指定MyBatis主配置文件位置
        String resource = "typehandle.xml";
        //2.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //3.创建SqlSessionFactory会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //4.通过会话工厂创建SqlSession会话
        SqlSession session = sqlSessionFactory.openSession();
        
        //SqlSession session = sqlSessionFactory.openSession(true);  //自动提交
        boolean autoCommit = session.getConnection().getAutoCommit();
        
        System.out.println("autoCommit = " + autoCommit);
        
        //5.执行mapper.xml文件里的sql语句，得到结果集合
        //方式一:生成动态代理类
        List<Map<String, Object>> list = session.selectList("queryAll");
        for (Map<String, Object> map : list) {
        	Object bornDate = map.get("BORN_DATE"); // datetime(6) -> java.sql.Timestamp
            System.out.println(bornDate.getClass());
        }
        
        EntityMapper mapper = session.getMapper(EntityMapper.class);
        
    
        
        
//      int i = mapper.insertOne("2022-02-16");
        session.commit();
    }
}
