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

public class Main {

	public static final String MYBATIS_CONFIGURATION_FILE_PATH = "mybatis-cache.xml";
	
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIGURATION_FILE_PATH);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        
        Student stu1 = studentMapper.queryByStuId("7b838d7a-741a-11ec-9b88-38142830461b");
        System.out.println(stu1);
        System.out.println("使用同一个sqlsession再执行一次");

        Student stu2 = studentMapper.queryByStuId("7b838d7a-741a-11ec-9b88-38142830461b");  //不会执行sql
        System.out.println(stu2);
    
        
//      第一个 SqlSession 实际只发生过一次查询，而第二次查询就从缓存中取出了，也就是 SqlSession 层面的一级缓存。
//      为了克服这个问题，我们往往需要配置二级缓存，使得缓存在 SqlSessionFactory 层面上能够提供给各个 SqlSession 对象共享
        System.out.println(stu1 == stu2); // true
        
        System.out.println("现在创建一个新的SqlSeesion对象在执行一次");
        SqlSession session2 = sqlSessionFactory.openSession();
        Student stu3 = session2.selectOne("code.example.mybatis.mapper.StudentMapper.queryByStuId", "7b838d7a-741a-11ec-9b88-38142830461b");
        System.out.println(stu3);
        //请注意，当我们使用二级缓存的时候，sqlSession调用了 commit方法后才会生效
        session2.commit();
    }
}
