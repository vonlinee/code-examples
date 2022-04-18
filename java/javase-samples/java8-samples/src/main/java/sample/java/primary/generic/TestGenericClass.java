package sample.java.primary.generic;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

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
			
		}
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
}
