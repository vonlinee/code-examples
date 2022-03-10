







AbstractPlatformTransactionManager

```java
private void processRollback(DefaultTransactionStatus status, boolean unexpected)
```





CglibAopProxy

CglibMethodInvocation

```java
static boolean isMethodProxyCompatible(Method method) {
    return (Modifier.isPublic(method.getModifiers()) &&
            method.getDeclaringClass() != Object.class && !AopUtils.isEqualsMethod(method) &&
            !AopUtils.isHashCodeMethod(method) && !AopUtils.isToStringMethod(method));
}
```

不代理

Only use method proxy for public methods not derived from java.lang.Object



org.springframework.cglib.proxy.MethodProxy



org.springframework.aop.framework.ReflectiveMethodInvocation



org.springframework.transaction.interceptor.TransactionInterceptor













