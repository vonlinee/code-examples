package io.maker.generator.sample;

import io.maker.generator.utils.XMLMapperLoader;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LMinY
 * @description: 开启自动刷新XML
 * @date 2020/8/27
 */
@Configuration
public class XMLMapperConfing {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 根据配置文件的值 是否开启实时刷新
     */
    @Value("${XMLMapperRefresh}")
    Boolean XMLMapperRefresh;

    @Bean
    public void xMLMapperLoader() {
        if (XMLMapperRefresh) {
            new XMLMapperLoader(sqlSessionFactory, "/mapper");
        }
    }
}