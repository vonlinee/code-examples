package sample.mybatis.interceptor;

import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts(@Signature(
		type = StatementHandler.class, method = "query", args = { Statement.class,
		ResultHandler.class, }))
public class StatementHandlerInterceptor implements Interceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatementHandlerInterceptor.class);
	
	/**
	 * 拦截作用，被代理对象的指定拦截的方法调用前会调用此方法
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object proceed = invocation.proceed();
		printlnInvocation(invocation);
		return proceed;
	}
	
	public void printlnInvocation(Invocation invocation) {
		Object target = invocation.getTarget();
		Method method = invocation.getMethod();
		Object[] args = invocation.getArgs();
		System.out.println(target);
		System.out.println(method);
		System.out.println(args);
	}

	// 使用动态代理为对象包装上一层代理
	@Override
	public Object plugin(Object target) {
		Object proxy = Plugin.wrap(target, this);
		LOGGER.info("target {} => proxy {}", target, proxy);
		// 元对象
		// MetaObject metaObject = SystemMetaObject.forObject(target);
		return proxy;
	}

	/**
	 * 这行调用时，调用setProperties方法
	 * SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	 */
	@Override
	public void setProperties(Properties properties) {
		LOGGER.info("properties => {}", properties);
	}
}
