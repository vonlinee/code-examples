package samples.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MetadataUtils {

	private static final String EXTENDS = "extends";
	private static final String IMPLEMENTS = "implements";
	
	//=====================================================================

	public static ClassDefinition parse(Class<?> clazz) {
		if (clazz == null) {
			throw new NullPointerException("class to be parsed cannot be null.");
		}
		if (clazz.isArray()) {
			throw new UnsupportedOperationException("array is not supported.");
		}
		// Object
		if (clazz == Object.class) {
			return parseObject();
		}
		ClassDefinition classDefinition = new ClassDefinition();
		classDefinition.setClassName(clazz.getName());
		classDefinition.setClassType(1); // Class
		Package package1 = clazz.getPackage();
		if (package1 != null) {
			classDefinition.setPackageName(package1.getName());
		}
		if (clazz.isInterface()) {
			classDefinition.setClassType(2);
		}
		if (clazz.isAnnotation()) {
			classDefinition.setClassType(4);
		}
		// find direct super class
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			classDefinition.getSuperClasses().put(superClass.getName(), superClass.getName());
			classDefinition.getImportList().add(superClass.getPackage().getName());
		}
		// find direct super interface
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> interfaceClass : interfaces) {
			classDefinition.getSuperInterfaces().put(interfaceClass.getName(), interfaceClass.getName());
			classDefinition.getImportList().add(interfaceClass.getPackage().getName());
		}
		// find super antotation recursivly
		Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
		for (Annotation annotation : declaredAnnotations) {
			String annotationTypeName = annotation.annotationType().getName();
			classDefinition.getDeclaredAnnotations().put(annotationTypeName, annotationTypeName);
			classDefinition.getImportList().add(annotation.annotationType().getPackage().getName());
		}
		
		// Fields
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			FieldDefinition fd = new FieldDefinition();
			fd.setBelongToClass(clazz.getName());
			fd.setModifiers(field.getModifiers());
			fd.setName(field.getName());
			classDefinition.getFieldDefinitions().add(fd);
		}
		
		// Method
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			MethodDefinition md = new MethodDefinition();
			md.setMethodName(method.getName());
			md.setModifiers(method.getModifiers());
			
			Annotation[] mdDeclaredAnnos = method.getDeclaredAnnotations();
			for(Annotation anno : mdDeclaredAnnos) {
				Class<? extends Annotation> type = anno.annotationType();
				md.addDeclaredAnnotation(type.getName(), type.getName());
			}
			classDefinition.getMethodDefinitions().add(md);
		}
		
		return classDefinition;
	}

	private static ClassDefinition parseObject() {
		ClassDefinition definition = new ClassDefinition();
		definition.setClassName("java.lang.Object".intern());
		definition.setClassType(1);
		return definition;
	}
	
}
