package org.example.bean;

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
    }
}
