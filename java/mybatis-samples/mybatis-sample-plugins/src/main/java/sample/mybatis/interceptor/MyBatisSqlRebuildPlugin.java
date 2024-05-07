package sample.mybatis.interceptor;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * 获取真实执行的 sql
 * <p>
 * <a href="https://www.cnblogs.com/larva-zhh/p/12191531.html">重写SQL</a>
 * </p>
 * <a href="https://cloud.tencent.com/developer/article/1734409">自定义插件</a>
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class})})
public class MyBatisSqlRebuildPlugin implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MyBatisSqlRebuildPlugin.class);

    private final SimpleAppendUpdateTimeVisitor visitor = new SimpleAppendUpdateTimeVisitor();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 这里的Args就是@Intercepts的args参数
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//		if (sqlCommandType != SqlCommandType.INSERT) {
//			return invocation.proceed();
//		}
        BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
        String sql = boundSql.getSql();
        // 解析SQL
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        if (!sqlStatements.isEmpty()) {
            for (SQLStatement sqlStatement : sqlStatements) {
                sqlStatement.accept(visitor);
            }
        }
        // 重写状态
        // 改写了SQL，需要替换MappedStatement
        String newSql = SQLUtils.toSQLString(sqlStatements, JdbcConstants.MYSQL);
        LOG.info("重写sql, 原始sql: [{}], \n重写之后sql: [{}]", sql, newSql);
        BoundSql newBoundSql = new BoundSql(
                mappedStatement.getConfiguration(),
                newSql,
                boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        // 复制原始MappedStatement的各项属性
        MappedStatement.Builder builder = new MappedStatement.Builder(
                mappedStatement.getConfiguration(),
                mappedStatement.getId(),
                new WarpBoundSqlSqlSource(newBoundSql),
                mappedStatement.getSqlCommandType());
        builder.cache(mappedStatement.getCache())
                .databaseId(mappedStatement.getDatabaseId())
                .fetchSize(mappedStatement.getFetchSize())
                .flushCacheRequired(mappedStatement.isFlushCacheRequired())
                .keyColumn(StringUtils.join(mappedStatement.getKeyColumns(), ','))
                .keyGenerator(mappedStatement.getKeyGenerator())
                .keyProperty(StringUtils.join(mappedStatement.getKeyProperties(), ','))
                .lang(mappedStatement.getLang()).parameterMap(mappedStatement.getParameterMap())
                .resource(mappedStatement.getResource()).resultMaps(mappedStatement.getResultMaps())
                .resultOrdered(mappedStatement.isResultOrdered())
                .resultSets(StringUtils.join(mappedStatement.getResultSets(), ','))
                .resultSetType(mappedStatement.getResultSetType()).statementType(mappedStatement.getStatementType())
                .timeout(mappedStatement.getTimeout()).useCache(mappedStatement.isUseCache());
        MappedStatement newMappedStatement = builder.build();
        // 将新生成的MappedStatement对象替换到参数列表中
        args[0] = newMappedStatement;
        return invocation.proceed();
    }

    /**
     * 生成代理类然后添加到{@link InterceptorChain}中
     * <p>
     * Mybatis的{@link Executor}依赖以下几个组件：
     * <ol>
     * <li>{@link StatementHandler} 负责创建JDBC {@link java.sql.Statement}对象</li>
     * <li>{@link ParameterHandler} 负责将实际参数填充到JDBC
     * {@link java.sql.Statement}对象中</li>
     * <li>{@link ResultSetHandler} 负责JDBC
     * {@link java.sql.Statement#execute(String)}
     * 后返回的{@link java.sql.ResultSet}的处理</li>
     * </ol>
     * 因为此Plugin只对Executor生效所以只代理{@link Executor}对象
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    static class WarpBoundSqlSqlSource implements SqlSource {

        private final BoundSql boundSql;

        public WarpBoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
