



{[closureParameters ->] statements}

```java
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else if (isDefaultMethod(method)) {
            return invokeDefaultMethod(proxy, method, args);
        }
    } catch (Throwable t) {
        throw ExceptionUtil.unwrapThrowable(t);
    }
    final MapperMethod mapperMethod = cachedMapperMethod(method);
    return mapperMethod.execute(sqlSession, args);
}
```

获取方法在哪个类中声明

```java
@Override
public Class<?> getDeclaringClass() {
    return clazz;
}
```







```java
public Object execute(SqlSession sqlSession, Object[] args) {
    Object result;
    switch (command.getType()) {
        case INSERT: {
            Object param = method.convertArgsToSqlCommandParam(args);
            result = rowCountResult(sqlSession.insert(command.getName(), param));
            break;
        }
        case UPDATE: {
            Object param = method.convertArgsToSqlCommandParam(args);
            result = rowCountResult(sqlSession.update(command.getName(), param));
            break;
        }
        case DELETE: {
            Object param = method.convertArgsToSqlCommandParam(args);
            result = rowCountResult(sqlSession.delete(command.getName(), param));
            break;
        }
        case SELECT:
            if (method.returnsVoid() && method.hasResultHandler()) {
                executeWithResultHandler(sqlSession, args);
                result = null;
            } else if (method.returnsMany()) {
                result = executeForMany(sqlSession, args);
            } else if (method.returnsMap()) {
                result = executeForMap(sqlSession, args);
            } else if (method.returnsCursor()) {
                result = executeForCursor(sqlSession, args);
            } else {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.selectOne(command.getName(), param);
            }
            break;
        case FLUSH:
            result = sqlSession.flushStatements();
            break;
        default:
            throw new BindingException("Unknown execution method for: " + command.getName());
    }
    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
        throw new BindingException("Mapper method '" + command.getName() 
                                   + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
    }
    return result;
}
```







MapperProxy缓存有Mapper的Method和对应的MapperMethod实例

```java
private final SqlSession sqlSession;
private final Map<Method, MapperMethod> methodCache;
```

MapperMethod调用需要SqlSession，SqlSession是方法实际执行者

```java
sqlSession.<E>selectList(command.getName(), param)
```

DefaultSqlSession：

```java
statement:方法全限定名称code.example.mybatis.mapper.TeacherMapper.queryAllTeacher
@Override
public <E> List<E> selectList(String statement, Object parameter) {
    return this.selectList(statement, parameter, RowBounds.DEFAULT);
}
```

org.apache.ibatis.executor.CachingExecutor

```java
@Override
public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
    try {
        MappedStatement ms = configuration.getMappedStatement(statement);
        return executor.query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);
    } catch (Exception e) {
        throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
    } finally {
        ErrorContext.instance().reset();
    }
}
```



```java
@Override
public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
    throws SQLException {
    // 默认走一级缓存
    Cache cache = ms.getCache();
    if (cache != null) {
        flushCacheIfRequired(ms);
        if (ms.isUseCache() && resultHandler == null) {
            ensureNoOutParams(ms, boundSql);
            @SuppressWarnings("unchecked")
            // private final TransactionalCacheManager tcm = new TransactionalCacheManager();
            List<E> list = (List<E>) tcm.getObject(cache, key);
            if (list == null) {
                list = delegate.<E> query(
                    ms, parameterObject, rowBounds, resultHandler, key, boundSql);
                tcm.putObject(cache, key, list); // issue #578 and #116
            }
            return list;
        }
    }
    //委托模式，实际执行交给org.apache.ibatis.executor.SimpleExecutor
    return delegate.<E> query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
}
```

org.apache.ibatis.executor.BaseExecutor

```java
@SuppressWarnings("unchecked")
@Override
public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
    if (closed) {
        throw new ExecutorException("Executor was closed.");
    }
    if (queryStack == 0 && ms.isFlushCacheRequired()) {
        clearLocalCache();
    }
    List<E> list;
    try {
        queryStack++;
        list = resultHandler == null ? (List<E>) localCache.getObject(key) : null;
        if (list != null) {
            handleLocallyCachedOutputParameters(ms, key, parameter, boundSql);
        } else {
            // 实际执行的查询
            list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
        }
    } finally {
        queryStack--;
    }
    if (queryStack == 0) {
        for (DeferredLoad deferredLoad : deferredLoads) {
            deferredLoad.load();
        }
        // issue #601
        deferredLoads.clear();
        if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
            // issue #482
            clearLocalCache();
        }
    }
    return list;
}
```



```java
private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    List<E> list;
    localCache.putObject(key, EXECUTION_PLACEHOLDER);
    try {
        // doQuery抽象方法，需要子类实现
        list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
    } finally {
        localCache.removeObject(key);
    }
    localCache.putObject(key, list);
    if (ms.getStatementType() == StatementType.CALLABLE) {
        localOutputParameterCache.putObject(key, parameter);
    }
    return list;
}
```

这里的实现是org.apache.ibatis.executor.SimpleExecutor

```java
@Override
public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
    Statement stmt = null;
    try {
        // MappedStatement获取全局配置
        Configuration configuration = ms.getConfiguration();
        // 创建StatementHandler
        StatementHandler handler = configuration.newStatementHandler(
            wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
        stmt = prepareStatement(handler, ms.getStatementLog());
        return handler.<E>query(stmt, resultHandler);
    } finally {
        closeStatement(stmt);
    }
}
```

这里wrapper是org.apache.ibatis.executor.CachingExecutor，即CachingExecutor中有一个委托Executor，SimpleExecutor中有一个wrapper也是Executor

这里wrapper是org.apache.ibatis.executor.CachingExecutor的意思是再次从缓存中获取？二级缓存

org.apache.ibatis.executor.statement.RoutingStatementHandler



```java
public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    // RoutingStatementHandler
    StatementHandler statementHandler = new RoutingStatementHandler(
        executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
    // 添加代理
    statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
    return statementHandler;
}
```

返回的StatementHandler是org.apache.ibatis.executor.statement.RoutingStatementHandler



org.apache.ibatis.executor.statement.PreparedStatementHandler

```java
@Override
public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
    Statement stmt = null;
    try {
        // MappedStatement获取全局配置
        Configuration configuration = ms.getConfiguration();
        // 创建StatementHandler
        StatementHandler handler = configuration.newStatementHandler(
            wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
        // 创建Statement对象
        stmt = prepareStatement(handler, ms.getStatementLog());
        return handler.<E>query(stmt, resultHandler);
    } finally {
        closeStatement(stmt);
    }
}
```





```java
@Override
public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
    // org.apache.ibatis.logging.jdbc.PreparedStatementLogger
    PreparedStatement ps = (PreparedStatement) statement;
    ps.execute();
    return resultSetHandler.<E> handleResultSets(ps);
}
```

这个PreparedStatementLogger又是PreparedStatement的一层代理，主要是为了执行sql的同时记录日志

```java
public final class PreparedStatementLogger extends BaseJdbcLogger implements InvocationHandler
```



org.apache.ibatis.logging.jdbc.ResultSetLogger是ResultSet的代理





```java
@Override
public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
    try {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, params);
        }          
        if (EXECUTE_METHODS.contains(method.getName())) {
            if (isDebugEnabled()) {
                debug("Parameters: " + getParameterValueString(), true);
            }
            clearColumnInfo();
            if ("executeQuery".equals(method.getName())) {
                ResultSet rs = (ResultSet) method.invoke(statement, params);
                return rs == null ? null : ResultSetLogger.newInstance(rs, statementLog, queryStack);
            } else {
                return method.invoke(statement, params);
            }
        } else if (SET_METHODS.contains(method.getName())) {
            if ("setNull".equals(method.getName())) {
                setColumn(params[0], null);
            } else {
                setColumn(params[0], params[1]);
            }
            // com.mysql.jdbc.JDBC42PreparedStatement，这里就已经到了驱动层了
            return method.invoke(statement, params);
        } else if ("getResultSet".equals(method.getName())) {
            ResultSet rs = (ResultSet) method.invoke(statement, params);
            return rs == null ? null : ResultSetLogger.newInstance(rs, statementLog, queryStack);
        } else if ("getUpdateCount".equals(method.getName())) {
            int updateCount = (Integer) method.invoke(statement, params);
            if (updateCount != -1) {
                debug("   Updates: " + updateCount, false);
            }
            return updateCount;
        } else {
            return method.invoke(statement, params);
        }
    } catch (Throwable t) {
        throw ExceptionUtil.unwrapThrowable(t);
    }
}
```



创建Statement的过程

```java
private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
    Statement stmt;
    Connection connection = getConnection(statementLog);
    stmt = handler.prepare(connection, transaction.getTimeout());
    handler.parameterize(stmt);
    return stmt;
}
```

获取连接对象的过程

```java
protected Connection getConnection(Log statementLog) throws SQLException {
    // transaction可以有不同的实现
    Connection connection = transaction.getConnection();
    if (statementLog.isDebugEnabled()) {
        return ConnectionLogger.newInstance(connection, statementLog, queryStack);
    } else {
        return connection;
    }
}
```

获取JDBC的对象Connection，Statement等，但实际拿到的都是其代理对象



org.apache.ibatis.transaction.jdbc.JdbcTransaction



在org.apache.ibatis.executor.BaseExecutor中获取连接

```java
@Override
public Connection getConnection() throws SQLException {
    if (connection == null) {
        openConnection();
    }
    return connection;
}

protected void openConnection() throws SQLException {
    if (log.isDebugEnabled()) {
        log.debug("Opening JDBC Connection");
    }
    // org.apache.ibatis.datasource.pooled.PooledDataSource
    connection = dataSource.getConnection();
    if (level != null) {
        connection.setTransactionIsolation(level.getLevel());
    }
    setDesiredAutoCommit(autoCommmit);
}
```

事务管理器获取连接，连接创建Statement，然后再为其进行代理

Executor直接同驱动层交互



Debug进不去某些jar包































