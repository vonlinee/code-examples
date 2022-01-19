package code.example.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * https://www.jianshu.com/p/50fcd7f127f0
 * https://blog.csdn.net/csucsgoat/article/details/116680557
 * @author someone
 */
public class PageHelperMain {

    public static void main(String[] args) throws IOException {
        //1.指定MyBatis主配置文件位置
        String resource = "mybatis-pagehelper.xml";
        //2.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //3.创建SqlSessionFactory会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //4.通过会话工厂创建SqlSession会话
        SqlSession session = sqlSessionFactory.openSession();
        //5.执行mapper.xml文件里的sql语句，得到结果集合
        //方式一:生成动态代理类
        List<Object> list = session.selectList("queryAll");
        for (Object obj : list) {
            System.out.println(obj);
        }
    }
}
