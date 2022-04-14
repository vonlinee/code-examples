package thirdlib.reflections;

import org.apache.ibatis.javassist.tools.reflect.Metaobject;
import org.reflections.Reflections;

public class Test1 {
	
	public static void main(String[] args) {
		Reflections reflections = new Reflections("com.my.project");

//		Set<Class<?>> subTypes =
//		  reflections.get(Test1.of(Test1.class).asClass());
//
//		Set<Class<?>> annotated = 
//		  reflections.get(SubTypes.of(TypesAnnotated.with(SomeAnnotation.class)).asClass());
		
	}
}
