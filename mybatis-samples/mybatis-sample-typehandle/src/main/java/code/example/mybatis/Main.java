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

public class Main {

    private static SqlSession openSession() {
        //1.指定MyBatis主配置文件位置
        String resource = "typehandle.xml";
        //2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.创建SqlSessionFactory会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //4.通过会话工厂创建SqlSession会话
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) throws IOException, SQLException {

        SqlSession sqlSession = openSession();

        //SqlSession session = sqlSessionFactory.openSession(true);  //自动提交
        boolean autoCommit = sqlSession.getConnection().getAutoCommit();

        System.out.println("autoCommit = " + autoCommit);

        //5.执行mapper.xml文件里的sql语句，得到结果集合
        //方式一:生成动态代理类
        // List<Map<String, Object>> list = sqlSession.selectList("queryAll");
        // for (Map<String, Object> map : list) {
        //     Object bornDate = map.get("BORN_DATE"); // datetime(6) -> java.sql.Timestamp
        //     System.out.println(bornDate.getClass());
        // }



//      int i = mapper.insertOne("2022-02-16");
        sqlSession.commit();
    }
}

//https://zhuanlan.zhihu.com/p/45044649
//很多人放弃了数据库的范式，极端追求数据库表的＂扁平化＂，大量使用扁平的表结构，去掉关联关系，大量使用冗余，很多所谓的架构师还理所当然的说在服务化的场景下这是绝对正确的；

// 领域建模

//Liquibase