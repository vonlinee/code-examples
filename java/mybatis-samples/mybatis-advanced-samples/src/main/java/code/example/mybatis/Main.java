package code.example.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import code.example.mybatis.component.MyBatisUtils;
import code.example.mybatis.entity.Example;
import code.example.mybatis.mapper.TeacherMapper;

public class Main {

	private static SqlSession openSession() {
		// 1.指定MyBatis主配置文件位置
		String resource = "mybatis-advanced.xml";
		// 2.加载配置文件
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 3.创建SqlSessionFactory会话工厂
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 4.通过会话工厂创建SqlSession会话
		return sqlSessionFactory.openSession();
	}

	public static void main(String[] args) throws IOException, SQLException {
		// testInsertJson();
		 test2();
//		test3();
	}

	public static void test1() {
		SqlSession sqlSession = openSession();

		// SqlSession session = sqlSessionFactory.openSession(true); //自动提交
		// boolean autoCommit = sqlSession.getConnection();

		// System.out.println("autoCommit = " + autoCommit);

		// 5.执行mapper.xml文件里的sql语句，得到结果集合
		// 方式一:生成动态代理类
		// List<Map<String, Object>> list = sqlSession.selectList("queryAll");
		// for (Map<String, Object> map : list) {
		// Object bornDate = map.get("BORN_DATE"); // datetime(6) -> java.sql.Timestamp
		// System.out.println(bornDate.getClass());
		// }

		TeacherMapper entityMapper = sqlSession.getMapper(TeacherMapper.class);

		List<Map<String, String>> list = entityMapper.queryTeacherAllBlob();
		for (Map<String, String> map : list) {
			System.out.println(map);
		}
	}

	public static void test2() {
		SqlSession sqlSession = openSession();

		// org.apache.ibatis.session.defaults.DefaultSqlSession

		// org.apache.ibatis.binding.MapperProxy

		TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
		teacherMapper.queryAllTeacher();

		sqlSession.close();
	}

	public static void test3() {
		// java bean
		Object javaBean = new Example().setContent("hello");
		MetaObject javaBeanMeta = MyBatisUtils.createMetaObject(javaBean);
		System.out.println(javaBeanMeta.getValue("content"));
		javaBeanMeta.setValue("content", "world");
		System.out.println(javaBeanMeta.getValue("content"));
	}

	/**
	 * CREATE TABLE `t_temp` ( `ID` varchar(36) NOT NULL, `EXTEND_JSON` json DEFAULT
	 * NULL, PRIMARY KEY (`ID`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
	 */
	public static void testInsertJson() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "zs");
		map.put("age", 12);
		Gson gson = new Gson();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("extendJson", JSON.toJSONString(map));
		SqlSession sqlSession = openSession();
		TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
		int i = mapper.insertJson(paramMap);
		sqlSession.commit();
	}
}

// https://zhuanlan.zhihu.com/p/45044649
// 很多人放弃了数据库的范式，极端追求数据库表的＂扁平化＂，大量使用扁平的表结构，去掉关联关系，大量使用冗余，很多所谓的架构师还理所当然的说在服务化的场景下这是绝对正确的；
// 领域建模
// Liquibase
// List<Map<String, UserInfoEntity>> selectUser
// 将存放对象的List转化为key值为对象某一属性的Map，对象属性名作为Map的key
// UserInfoEntity selectUser
