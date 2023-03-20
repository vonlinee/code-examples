package org.example.bean;

<<<<<<< HEAD
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class Response {

    private Result<List<Employee>> result;

    public Result<List<Employee>> getResult() {
        return result;
    }

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        Field field = Response.class.getDeclaredField("result");
        field.setAccessible(true);
        Type genericType = field.getGenericType();

        System.out.println(genericType);

        Method method = Response.class.getDeclaredMethod("getResult");
        method.setAccessible(true);
        Type genericReturnType = method.getGenericReturnType();
=======
import sun.reflect.generics.factory.CoreReflectionFactory;
import sun.reflect.generics.factory.GenericsFactory;
import sun.reflect.generics.repository.FieldRepository;
import sun.reflect.generics.scope.ClassScope;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Response<E, T> {

    private Result<List<Employee>> f1;
    private List<Employee> f2;
    private List<T> f3;
    private List<?> f4;

    public static ParameterizedType test1() throws NoSuchFieldException {

        Field field = new Response<String, Integer>().getClass().getDeclaredField("f3");

        field.setAccessible(true);
        Type genericType = field.getGenericType();
        return (ParameterizedType) genericType;
    }

    public static void main(String[] args) throws NoSuchFieldException {

        long s1 = System.currentTimeMillis();
        ParameterizedType type = make("Lorg/example/bean/Result<Ljava/util/List<Lorg/example/bean/Employee;>;>;", Response.class);
        System.out.println(System.currentTimeMillis() - s1);
        ParameterizedType type2 = test1();
        System.out.println(System.currentTimeMillis() - s1);
        // System.out.println(type.equals(type2));
    }

    /**
     * 手动制作ParameterizedType
     *
     * @param genericSignature 泛型签名，字段的泛型签名
     * @param declaringClass   字段声明所在的类
     * @return 泛型类型，即ParameterizedType
     */
    public static ParameterizedType make(String genericSignature, Class<?> declaringClass) {
        // 获取GenericsFactory
        GenericsFactory genericsFactory = CoreReflectionFactory.make(declaringClass, ClassScope.make(declaringClass));
        // 构造FieldRepository
        FieldRepository fieldRepository = FieldRepository.make(genericSignature, genericsFactory);
        // 最后一步: 得到泛型类型，即ParameterizedType
        Type genericType = fieldRepository.getGenericType();
        if (genericType instanceof ParameterizedType) {
            return (ParameterizedType) genericType;
        }
        return null;
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947
    }
}
