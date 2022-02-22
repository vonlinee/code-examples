



https://www.mybatis-plus.com/guide/quick-start.html#%E9%85%8D%E7%BD%AE


自动驼峰命名
/*
实体类中的字段 userName 自动映射对应数据库中的 user_name,
如果数据库表中字段不是 user_name 字段，而是 userName
此时Java程序就会报错
*/

关闭自动映射
<!--在spring 的 mybatis 的配置文件中添加配置 -->
<settings>
    <setting name="mapUnderscoreToCamelCase" value="false"/>
</settings>

#在springboot的yml配置文件中配置
mybatis-plus:
configuration:
map-underscore-to-camel-case: false



```java
private MapperMethodInvoker cachedInvoker(Method method) throws Throwable {
    try {
        return CollectionUtils.computeIfAbsent(methodCache, method, m -> {
            if (m.isDefault()) {
                try {
                    if (privateLookupInMethod == null) {
                        return new DefaultMethodInvoker(getMethodHandleJava8(method));
                    } else {
                        return new DefaultMethodInvoker(getMethodHandleJava9(method));
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                         | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 普通方法
                return new PlainMethodInvoker(new MybatisMapperMethod(mapperInterface, method, sqlSession.getConfiguration()));
            }
        });
    } catch (RuntimeException re) {
        Throwable cause = re.getCause();
        throw cause == null ? re : cause;
    }
}
```





org.apache.ibatis.binding.MapperMethod.SqlCommand



```java
public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
    final String methodName = method.getName();
    final Class<?> declaringClass = method.getDeclaringClass();
    MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass, configuration);
    if (ms == null) {
        if (method.getAnnotation(Flush.class) != null) {
            name = null;
            type = SqlCommandType.FLUSH;
        } else {
            throw new BindingException("Invalid bound statement (not found): "
                                       + mapperInterface.getName() + "." + methodName);
        }
    } else {
        name = ms.getId();
        type = ms.getSqlCommandType();
        if (type == SqlCommandType.UNKNOWN) {
            throw new BindingException("Unknown execution method for: " + name);
        }
    }
}
```





org.apache.ibatis.annotations.Flush

执行flush语句通过mapper接口





```java
private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                               Class<?> declaringClass, Configuration configuration) {
    String statementId = mapperInterface.getName() + "." + methodName;
    if (configuration.hasStatement(statementId)) {
        return configuration.getMappedStatement(statementId);
    } else if (mapperInterface.equals(declaringClass)) { //mapper接口就是mapper声明的类
        return null;
    }
    for (Class<?> superInterface : mapperInterface.getInterfaces()) {
        if (declaringClass.isAssignableFrom(superInterface)) {
            MappedStatement ms = resolveMappedStatement(superInterface, methodName,
                                                        declaringClass, configuration);
            if (ms != null) {
                return ms;
            }
        }
    }
    return null;
}
}
```

