













控制台提示以下内容

```plain
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.apache.ibatis.reflection.Reflector (file:/D:/Develop/MavenLocalRepo/org/mybatis/mybatis/3.4.6/mybatis-3.4.6.jar) to method java.lang.Class.checkPackageAccess(java.lang.SecurityManager,java.lang.ClassLoader,boolean)
WARNING: Please consider reporting this to the maintainers of org.apache.ibatis.reflection.Reflector
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```

压制警告，这里会抛异常，但是被捕获且未作处理

```java
public static void disableWarning() {
    try {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe u = (Unsafe) theUnsafe.get(null);
        Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
        Field logger = cls.getDeclaredField("logger");
        u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
    } catch (Exception e) {
        //ignore ，肯定会抛异常
    }
}
```















