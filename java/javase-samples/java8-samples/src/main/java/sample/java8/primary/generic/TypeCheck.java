package sample.java8.primary.generic;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeCheck<T> {

    public static void main(String[] args) {
        TypeCheck typeCheck = new TypeCheck();

        List<String> strs = new ArrayList<>();
        strs.add("1");

        println(strs);

    }

    static <T> TypeCheck<T> test() {
        return new TypeCheck<>();
    }

    public static <T> void println(List<T> list) {
        Class<?> clazz = list.getClass();
        TypeVariable<? extends Class<?>>[] typeParameters = clazz.getTypeParameters();
        for (TypeVariable<? extends Class<?>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getName());  // E
            System.out.println(typeParameter.getTypeName());

            Class<?> genericDeclaration = typeParameter.getGenericDeclaration();

            for (TypeVariable<? extends Class<?>> parameter : genericDeclaration.getTypeParameters()) {
                System.out.println(parameter);
            }

            System.out.println("Bounds");
            Type[] bounds = typeParameter.getBounds();
            if (bounds != null) {
                for (Type bound : bounds) {
                    System.out.println(bound.getClass());
                }
            }
        }
    }

    public static void printlnTypeVariables(TypeVariable<? extends Class<?>>[] typeVariables) {
        if (typeVariables != null) {
            for (TypeVariable<? extends Class<?>> typeVariable : typeVariables) {

            }
        }
    }
}
