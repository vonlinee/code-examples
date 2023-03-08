package sample.java8.primary.generic;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sample.java8.primary.generic.bean.Child;
import sample.java8.primary.generic.bean.Fruit;

public class TestGenericClass {

    public static void main(String[] args) {

//		Child is a raw type. References to generic type Child<P,GP> should be parameterized
        TypeVariable<Class<Child>>[] typeParameters = Child.class.getTypeParameters();

        for (int i = 0; i < typeParameters.length; i++) {
            // 接口
            TypeVariable<Class<Child>> typeVariable = typeParameters[i];
            // class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
            System.out.println(typeVariable.getClass());

            System.out.println("泛型参数名：" + typeVariable.getName());
            System.out.println("泛型类型名：" + typeVariable.getTypeName());

            getBounds(typeVariable);

            ArrayList<Object> strings = new ArrayList<>();
            strings.<String>add("");
        }

        TestGenericClass.<String>name();
    }

    public static <T> void name() {

    }

    public static void getBounds(TypeVariable<? extends GenericDeclaration> typeVariable) {
        Type[] bounds = typeVariable.getBounds();
        for (Type type : bounds) {
            System.out.println(type);
        }
        AnnotatedType[] annotatedBounds = typeVariable.getAnnotatedBounds();
        for (AnnotatedType annotatedType : annotatedBounds) {
            System.out.println(annotatedType);
        }
        System.out.println("========================================");
    }

    public static void test2() {
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
// Type mismatch: cannot convert from ArrayList<String> to ArrayList<Object>
        //list1 = list2;
// Type mismatch: cannot convert from ArrayList<Object> to ArrayList<String>
        list1.add(new Object());
        list1.add(new Date()); // 编译不报错：Date可向上转型为Object
    }

    public static double sum(List<? extends Number> list) {
        double result = 0;
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i).doubleValue();
        }
        // The method add(int, capture#3-of ? extends Number) in the
        // type List<capture#3-of ? extends Number>
        // is not applicable for the arguments (int)
        // list.add(10f);  // 编译错误!
        Number number = list.get(1);
        return result;
    }
    
    
    
    private void printList(List<Fruit> list) {
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
