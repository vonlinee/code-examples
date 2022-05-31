package io.maker.codegen.sample;

import org.apache.ibatis.session.SqlSessionFactory;
import io.maker.codegen.utils.XMLMapperLoader;

/**
 * 开启自动刷新XML
 */
public class XMLMapperConfing {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 根据配置文件的值 是否开启实时刷新
     */
    Boolean XMLMapperRefresh;

    public void xMLMapperLoader() {
        if (XMLMapperRefresh) {
            new XMLMapperLoader(sqlSessionFactory, "/mapper");
        }
    }
}