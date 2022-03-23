package sample.java.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

interface Demo1I1 { //@1
}

interface Demo1I2 { //@2
}

/**
 * 类型参数变量
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class Demo1<T1, T2 extends Integer, T3 extends Demo1I1 & Demo1I2> { //@3

    public static void main(String[] args) {
        TypeVariable<Class<Demo1>>[] typeParameters = Demo1.class.getTypeParameters();//@4
        
        System.out.println(typeParameters.length);
        
        for (TypeVariable<Class<Demo1>> typeParameter : typeParameters) {
            System.out.println("变量名称:" + typeParameter.getName());
            
            System.out.println("变量类型名称:" + typeParameter.getTypeName());
            System.out.println("这个变量在哪声明的:" + typeParameter.getGenericDeclaration());
            //获取上边界
            Type[] bounds = typeParameter.getBounds();
            System.out.println("这个变量上边界数量:" + bounds.length);
            System.out.print("这个变量上边界清单:");  //如果明确指定了一个父类，那么就不包括Object
            for (Type bound : bounds) {
                System.out.print(bound.getTypeName() + "  ");
            }
            
            AnnotatedType[] annotatedBounds = typeParameter.getAnnotatedBounds();
            
            Annotation[] declaredAnnotations = typeParameter.getDeclaredAnnotations();
            
            
            System.out.println("\n--------------------");
        }
    }
}