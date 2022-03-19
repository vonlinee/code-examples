package code.example.mybatis;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import code.example.mybatis.entity.Student;
import code.example.mybatis.mapper.StudentMapper;

/**
 * https://mybatis.org/mybatis-3/zh/java-api.html
 * @author ly-wangliang
 */
public class Main {

    public static void main(String[] args) throws IOException {
       	SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    	SqlSessionFactory factory = builder.build(initMyBatisConfiguration());
    	
    	SqlSession session = factory.openSession();
//    	MetaObject
    	StudentMapper studentMapper = session.getMapper(StudentMapper.class);
    	List<Student> students = studentMapper.queryAll();
    	
    }
    
    public static DataSource initDataSource() throws IOException {
    	Properties properties = Resources.getResourceAsProperties("jdbc.properties");
    	
    	properties = adaptedMybatisProperties(properties);
    	
    	PooledDataSourceFactory factory = new PooledDataSourceFactory();
    	factory.setProperties(properties);
    	return factory.getDataSource();
    }
    
    /**
     * org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory
     * 要求的属性前缀是driver.
     * @param properties
     * @return
     */
    private static Properties adaptedMybatisProperties(Properties properties) {
    	String prefix = "driver.";
    	Properties props = new Properties();
    	for (Entry<Object, Object> entry : properties.entrySet()) {
    		Object key = entry.getKey();
    		Object value = entry.getValue();
    		if (key instanceof String) {
    			String propertyName = (String) key;
        		if (!propertyName.startsWith(prefix)) {
        			int i = propertyName.lastIndexOf(".");
        			propertyName = prefix + propertyName.substring(i + 1);
        		}
        		props.put(propertyName, value);
			}
		}
    	return props;
    }
    
    public static Configuration initMyBatisConfiguration() throws IOException {
    	DataSource dataSource = initDataSource();
    	TransactionFactory transactionFactory = new JdbcTransactionFactory();
    	Environment environment = new Environment("development", transactionFactory, dataSource);
    	Configuration configuration = new Configuration(environment);
    	configuration.setLazyLoadingEnabled(true);
//    	configuration.setEnhancementEnabled(true);
    	configuration.getTypeAliasRegistry().registerAlias(Student.class);
    	configuration.addMapper(StudentMapper.class);
    	return configuration;
    }
}
