package code.example.mybatis.test;

import code.example.mybatis.entity.Teacher;
import code.example.mybatis.mapper.TeacherMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {

    private SqlSession openSession() {
        //1.指定MyBatis主配置文件位置
        String resource = "mybatis-config.xml";
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


    @Test
    public void test1() {
        SqlSession sqlSession = openSession();
        System.out.println(sqlSession.getConfiguration().getDefaultExecutorType());

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher = new Teacher();
        teacher.setTeacherNo("T00032");
        teacher.setTeacherSex("女");
        teacher.setDepartName("D-0001");
        teacher.setNativePlace("China");
        teacher.setTeacherName("SooYoonJoo");
        teacher.setTelePhone("K1091021920");

        int i = mapper.insertTeacher(teacher);

        System.out.println(i);

        sqlSession.commit();
    }
}
