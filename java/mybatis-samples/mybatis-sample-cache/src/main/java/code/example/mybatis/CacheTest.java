package code.example.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import code.example.mybatis.entity.Student;
import code.example.mybatis.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CacheTest {

    public static final String MYBATIS_CONFIGURATION_FILE_PATH = "mybatis-cache.xml";

    private SqlSession session;

    SqlSessionFactory sqlSessionFactory;

    @BeforeAll
    void loadMyBatisConfig() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(MYBATIS_CONFIGURATION_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @BeforeEach
    public void openSession() {
        session = sqlSessionFactory.openSession();
        System.out.println(">>>>>>>>>>>>>>>> open session <<<<<<<<<<<<<<<<<< [" + session.hashCode() + "]");
    }

    final String id = "7b838d7a-741a-11ec-9b88-38142830461b";

    @Test
    public void testCache1() {
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        Student stu1 = studentMapper.queryByStuId(id); // 获取连接查询数据库
        Student stu2 = studentMapper.queryByStuId(id); // 从缓存中获取
        System.out.println(stu1 == stu2);
        // DefautlSqlSession
        // CachingExecutor
        StudentMapper studentMapper1 = session.getMapper(StudentMapper.class);
        Student stu3 = studentMapper1.queryByStuId(id);
        System.out.println(stu1 == stu3);
    }

    /**
     * 未启用二级缓存，默认开启一级缓存（会话级别）
     */
    @Test
    public void testCache2() {
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        Student stu1 = studentMapper.queryByStuNoUseCache("S005"); // 获取连接查询数据库
        //
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Student stu2 = sqlSession.getMapper(StudentMapper.class).queryByStuNoUseCache("S005");// 从缓存中获取
        System.out.println(stu1 == stu2); // false, 一级缓存
    }

    /**
     * 启用二级缓存，默认开启一级缓存（会话级别）
     * 添加<cache/></cache>标签
     */
    @Test
    public void testCache3() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession(false);
        Student stu1 = sqlSession1.getMapper(StudentMapper.class).queryByStuNoUseCache("S005"); // 获取连接查询数据库
        //
        SqlSession sqlSession2 = sqlSessionFactory.openSession(false);
        Student stu2 = sqlSession2.getMapper(StudentMapper.class).queryByStuNoUseCache("S005");// 从缓存中获取
        System.out.println(stu1 == stu2); // false, 一级缓存

        sqlSession1.commit();
        sqlSession2.commit();
        System.out.println(stu1 == stu2); // false, 一级缓存
    }
opppppoo
    @Test
    public void test1() {
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);

        Student stu1 = studentMapper.queryByStuId("7b838d7a-741a-11ec-9b88-38142830461b");
        System.out.println(stu1);
        System.out.println("使用同一个Session再执行一次");
        Student stu2 = studentMapper.queryByStuId("7b838d7a-741a-11ec-9b88-38142830461b");  //不会执行sql
        System.out.println(stu2);


//      第一个 SqlSession 实际只发生过一次查询，而第二次查询就从缓存中取出了，也就是 SqlSession 层面的一级缓存。
//      为了克服这个问题，我们往往需要配置二级缓存，使得缓存在 SqlSessionFactory 层面上能够提供给各个 SqlSession 对象共享
        System.out.println(stu1 == stu2); // true

        System.out.println("现在创建一个新的SqlSeesion对象在执行一次");
        SqlSession session2 = sqlSessionFactory.openSession();
        Student stu3 = session2.selectOne("code.example.mybatis.mapper.StudentMapper.queryByStuId", "7b838d7a-741a-11ec-9b88-38142830461b");
        System.out.println(stu3);
        System.out.println(stu3 == stu1); // false
        //请注意，当我们使用二级缓存的时候，sqlSession调用了commit方法后才会生效
        session2.commit();
    }
}
