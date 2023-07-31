package net.maku.generator.service;

import mybatis.MappedStatementParseResult;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * MyBatis Service
 */
public interface MyBatisService {

    MappedStatementParseResult parseMapperStatement(String mapperStatement);

    /**
     * 获取可执行的SQL
     * @param mappedStatement MappedStatement
     * @param boundSql        BoundSql
     * @param parameterObject 参数对象
     * @return 可执行的SQL
     */
    String getExecutableSql(MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject);
}
