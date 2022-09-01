package sample.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchExecutor;
import sample.mybatis.entity.Student;
import sample.mybatis.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import sample.mybatis.mapper.TeacherMapper;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Test;
import sample.mybatis.plugin.MyInterceptor;

public class Main {

    static Configuration configuration;

    //1.指定MyBatis主配置文件位置
    public static final String resource = "mybatis-typehandle.xml";

    private static final SqlSessionFactory sqlSessionFactory;

    static {
        sqlSessionFactory = sqlSessionFactory();
        configuration = sqlSessionFactory.getConfiguration();
    }

    private static SqlSessionFactory sqlSessionFactory() {
        //2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.创建SqlSessionFactory会话工厂
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    private static SqlSession openSession(boolean autoCommit) {
        //4.通过会话工厂创建SqlSession会话
        return sqlSessionFactory.openSession(autoCommit);
    }

    @Test
    public void test5() {
        //1.指定MyBatis主配置文件位置
        String resource = "mybatis-typehandle.xml";
        //2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSession session = SqlSessionManager.newInstance(inputStream);
        session.select("", null);
    }

    @Test
    public void test6() {
        batchInsert3();
    }

    @Test
    public void test1() {
        SqlSession sqlSession = openSession(true);

        //SqlSession session = sqlSessionFactory.openSession(true);  //自动提交
//        boolean autoCommit = sqlSession.getConnection();

//        System.out.println("autoCommit = " + autoCommit);

        //5.执行mapper.xml文件里的sql语句，得到结果集合
        //方式一:生成动态代理类
        // List<Map<String, Object>> list = sqlSession.selectList("queryAll");
        // for (Map<String, Object> map : list) {
        //     Object bornDate = map.get("BORN_DATE"); // datetime(6) -> java.sql.Timestamp
        //     System.out.println(bornDate.getClass());
        // }

        TeacherMapper entityMapper = sqlSession.getMapper(TeacherMapper.class);

        List<Map<String, String>> list = entityMapper.queryTeacherAllBlob();
        for (Map<String, String> map : list) {
            System.out.println(map);
        }
    }

    @Test
    public void test2() {
        SqlSession sqlSession = openSession(true);

//    	org.apache.ibatis.session.defaults.DefaultSqlSession

//    	org.apache.ibatis.binding.MapperProxy
        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

        List<Map<String, Object>> list = teacherMapper.queryAllTeacher();

        System.out.println(list);

        DefaultSqlSession session = (DefaultSqlSession) sqlSession;

        session.selectList("");
        session.update("");
        session.delete("");

        sqlSession.close();
    }

    int rowCount = 2;

    List<Student> studentList = prepareStudentData(rowCount);

    @Test
    public void testBatchInsert() {
        // batchInsert1();
        // batchInsert2();
        batchInsert3();
    }

    @Test
    public void testInterceptor() {
        configuration.addInterceptor(new MyInterceptor());
        batchInsert3();
    }

    private void batchInsert1() {
        SqlSession sqlSession = openSession(true);
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        long start = System.currentTimeMillis();
        for (Student student : studentList) {
            mapper.insertOne(student);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        System.out.println("delete => " + mapper.deleteAll());
    }

    // Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException: Lock wait timeout exceeded; try restarting transaction
    private void batchInsert2() {
        SqlSession sqlSession = openSession(true);
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        long start = System.currentTimeMillis();
        mapper.insertBatchWithForeach(studentList);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        System.out.println("delete => " + mapper.deleteAll());
    }

    /**
     * ExecutorType.BATCH
     */
    private void batchInsert3() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        BatchExecutor executor = null;
        long start = System.currentTimeMillis();
        for (Student student : studentList) {
            mapper.insertOne(student);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        System.out.println("delete => " + mapper.deleteAll());
    }

    private List<Student> prepareStudentData(int size) {
        List<Student> studentList = new ArrayList<>();
        Student student;
        for (int i = 0; i < size; i++) {
            student = new Student();
            student.setName("小王" + i);
            student.setGender(i < size / 2);
            student.setGrade(i);
            student.setScore(i);
            studentList.add(student);
        }
        return studentList;
    }

    /**
     * 插入JSON字段
     * CREATE TABLE `t_temp` (
     * `ID` varchar(36) NOT NULL,
     * `EXTEND_JSON` json DEFAULT NULL,
     * PRIMARY KEY (`ID`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
     */
    public static void testInsertJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zs");
        map.put("age", 12);
        Gson gson = new Gson();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("extendJson", JSON.toJSONString(map));
        SqlSession sqlSession = openSession(true);
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        int i = mapper.insertJson(paramMap);
        sqlSession.commit();
    }
}

//https://zhuanlan.zhihu.com/p/45044649
//很多人放弃了数据库的范式，极端追求数据库表的＂扁平化＂，大量使用扁平的表结构，去掉关联关系，大量使用冗余，很多所谓的架构师还理所当然的说在服务化的场景下这是绝对正确的；

// 领域建模

//Liquibase

//List<Map<String, UserInfoEntity>> selectUser
//将存放对象的List转化为key值为对象某一属性的Map，对象属性名作为Map的key
// UserInfoEntity selectUser










