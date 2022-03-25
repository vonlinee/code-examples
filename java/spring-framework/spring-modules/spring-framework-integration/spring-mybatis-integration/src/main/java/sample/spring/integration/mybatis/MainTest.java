package sample.spring.integration.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sample.spring.integration.mybatis.config.AnnotationConfiguration;
import sample.spring.integration.mybatis.config.XmlConfiguration;
import sample.spring.integration.mybatis.mapper.MultiDataSourceMapper;

/**
 * Caused by: org.xml.sax.SAXException: Invalid system identifier:
 * http://mybatis.org/dtd/mybatis-3-config.dtd
 */
public class MainTest {

    private static final ApplicationContext xmlConfigContext;
    private static final ApplicationContext annoConfigContext;

    static {
        xmlConfigContext = new AnnotationConfigApplicationContext(XmlConfiguration.class);
        annoConfigContext = new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
    }

    public static void main(String[] args) {
        test1();
    }

//	=========================== 测试方法 START =====================================================

    /**
     * org.mybatis.spring.transaction.SpringManagedTransaction
     * org.springframework.jdbc.datasource.DataSourceUtils
     * org.mybatis.spring.transaction.SpringManagedTransactionFactory
     * 注解@Lazy
     * 注解@ConditionalOnProperty
     * #数据库(主库)事务策略 普通: normal 多库(jta) : jta 多服务:tcc
     * write.mp.jdbc.transactionPolicy = jta
     * io.seata.tm.api.TransactionalExecutor
     */
    public static void test1() {
        MultiDataSourceMapper mapper = xmlConfigContext.getBean(MultiDataSourceMapper.class);
        List<Map<String, Object>> list = mapper.queryFromMultiDataSource();

        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            System.out.println("==========================================");
        }
    }

//	=========================== 测试方法 END =====================================================

}
