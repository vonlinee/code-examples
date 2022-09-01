package sample.mybatis.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

/**
 * MyBatis在实例化Executor、ParameterHandler、ResultSetHandler、StatementHandler四大接口对象的时候
 * 会调用interceptorChain.pluginAll()方法，该方法会循环执行拦截器链所有的拦截器的plugin()方法，
 * MyBatis提供了Plugin.wrap()方法用于生成代理
 */
@Intercepts({
        @Signature(method = "doUpdate", type = BatchExecutor.class, args = {MappedStatement.class, Object.class,}),
        @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}),
        @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class MyInterceptor implements Interceptor {

    @Override
    public Object plugin(Object target) {
        if (target.getClass() == BatchExecutor.class) return target;
        // 生成动态代理
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties);
    }

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        // 该方法写入自己的逻辑
        if (invocation.getTarget() instanceof StatementHandler) {

        }
        return invocation.proceed();
    }
}