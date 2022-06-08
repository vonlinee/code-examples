package sample.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import com.github.pagehelper.PageHelper;

import sample.mybatis.entity.Student;
import sample.mybatis.mapper.StudentMapper;

/**
 * https://www.jianshu.com/p/50fcd7f127f0
 * https://blog.csdn.net/csucsgoat/article/details/116680557
 * 
 * @author someone
 */
public class PluginMain {

	public static void main(String[] args) throws IOException {
		// 1.指定MyBatis主配置文件位置
		String resource = "mybatis-plugin.xml";
		// 2.加载配置文件
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 3.创建SqlSessionFactory会话工厂
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 4.通过会话工厂创建SqlSession会话
		SqlSession session = sqlSessionFactory.openSession();

		// 返回Mapper接口的代理对象 org.apache.ibatis.binding.MapperProxy@337d0578
		StudentMapper studentMapper = session.getMapper(StudentMapper.class);
		System.out.println("代理类 => " + studentMapper); // 执行代理类的toString方法
		List<Student> students = studentMapper.queryAll();

		for (Student student : students) {
			System.out.println(student);
		}

		if (session instanceof DefaultSqlSession) {
			DefaultSqlSession sqlSession = (DefaultSqlSession) session;
			List<Student> selectList = sqlSession.selectList("queryAll");
		}

		closeQuitely(session);
	}

	// 使用方式：https://pagehelper.github.io/docs/howtouse/

	public static void test1(SqlSession session) {
		PageHelper.startPage(1, 3);
		// 在你需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可，
		// 紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页
		List<Object> list = session.selectList("queryAll");
		for (Object obj : list) {
			System.out.println(obj);
		}
	}

	public static void test2(SqlSession session) {
		// 分页插件检测到使用了RowBounds参数时，就会对该查询进行物理分页
		List<Object> list = session.selectList("queryAll", null, new RowBounds(1, 5));
		for (Object obj : list) {
			System.out.println(obj);
		}
		// 不只有命名空间方式可以用RowBounds，使用接口的时候也可以增加RowBounds参数
	}

	private static void closeQuitely(SqlSession session) {
		if (session != null) {
			session.close();
		}
	}


}
