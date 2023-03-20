package org.example.bean;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Response {

    private Result<List<Employee>> result;

   public static void main(String[] args) throws NoSuchFieldException {
      Field field = Response.class.getDeclaredField("result");
      field.setAccessible(true);
      Type genericType = field.getGenericType();

      ParameterizedType type = (ParameterizedType) genericType;

      Type ownerType = type.getOwnerType();
      Type rawType = type.getRawType();
      Type[] actualTypeArguments = type.getActualTypeArguments();

      System.out.println();
   }
}
