package code.example.mybatis.plugin;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = {
				Connection.class, Integer.class
		})
})
public class SqlPrepareInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("===================================");
		// org.apache.ibatis.executor.statement.RoutingStatementHandler
		StatementHandler handler = (StatementHandler) realTarget(invocation.getTarget());
		MetaObject statementHandler = SystemMetaObject.forObject(handler);
		MappedStatement mappedStatement = (MappedStatement) statementHandler.getValue("delegate.mappedStatement");
		BoundSql boundSql = handler.getBoundSql();
		String id = mappedStatement.getId();
		String wrappedSql = null;
		if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
			statementHandler.setValue("delegate.boundSql.sql", wrappedSql);
		}
		if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
			statementHandler.setValue("delegate.boundSql.sql", wrappedSql);
		}
		if (mappedStatement.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
			statementHandler.setValue("delegate.boundSql.sql", wrappedSql);
		}
		return invocation.proceed();
	}

	/**
	 * 去掉层层代理，返回被代理对象
	 * @param target
	 * @return
	 * T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T realTarget(Object target) {
		if (Proxy.isProxyClass(target.getClass())) {
			MetaObject metaObject = SystemMetaObject.forObject(target);
			return realTarget(metaObject.getValue("h.target"));
		} else {
			return (T) target;
		}
	}

	/**
	 * 插件代理
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
}