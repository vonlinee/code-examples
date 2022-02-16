



```java
public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    executorType = executorType == null ? defaultExecutorType : executorType;
    executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    Executor executor;
    if (ExecutorType.BATCH == executorType) {
        executor = new BatchExecutor(this, transaction);
    } else if (ExecutorType.REUSE == executorType) {
        executor = new ReuseExecutor(this, transaction);
    } else {
        executor = new SimpleExecutor(this, transaction);
    }
    if (cacheEnabled) {
        executor = new CachingExecutor(executor);
    }
    executor = (Executor) interceptorChain.pluginAll(executor);
    return executor;
}
```



plugin

指定拦截的方法签名

```java
@SuppressWarnings("rawtypes")
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageHelper extends BasePageHelper implements Interceptor {
    private final SqlUtil sqlUtil = new SqlUtil();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return sqlUtil.intercept(invocation);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        sqlUtil.setProperties(properties);
    }
}
```



Plugin.wrap(target, this)

获取拦截器上指定拦截的方法签名，生成拦截特定方法的代理类

```java
public static Object wrap(Object target, Interceptor interceptor) {
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    Class<?> type = target.getClass();
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
        return Proxy.newProxyInstance(
            type.getClassLoader(),
            interfaces,
            new Plugin(target, interceptor, signatureMap)); //Plugin -> InvocationHandler
    }
    return target;
}
```

Plugin就是对应的InvocationHandler

public class Plugin implements InvocationHandler

```java
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
        Set<Method> methods = signatureMap.get(method.getDeclaringClass());
        if (methods != null && methods.contains(method)) {
            return interceptor.intercept(new Invocation(target, method, args));
        }
        return method.invoke(target, args);
    } catch (Exception e) {
        throw ExceptionUtil.unwrapThrowable(e);
    }
}
```

拦截的方法都保存在signatureMap





多重代理

com.github.pagehelper.util.SqlUtil.doIntercept(Invocation)



结合PageHelper的使用方法来推原理

执行selectList方法时

```java
<E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds,
                  ResultHandler resultHandler) throws SQLException;

```

代理拦截了方法，实际执行下面的方法，并给sql拼上了分页参数

```java
<E> List<E> query(
    MappedStatement ms, Object parameter, RowBounds rowBounds, 
    ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) 
    throws SQLException;
```



CachingExecutor

InterceptorChain

1.xml -> Interceptor / Plugin
	Configuration
	InterceptorChain
2.SqlSession session = sqlSessionFactory.openSession();
	Executor -> ExecutorProxy
