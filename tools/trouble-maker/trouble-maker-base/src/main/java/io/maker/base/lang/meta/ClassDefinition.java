package io.maker.base.lang.meta;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Java的类定义：用于FreeMarker的模板文件映射
 */
public class ClassDefinition implements Serializable, Comparable<ClassDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String EXTENDS = "extends";
	private static final String IMPLEMENTS = "implements";

	private boolean isInner = false; // 是否是内部类

	/**
	 * Class-1, Interface-2, Annotation-4, Array-8
	 */
	private int classType;

	private String className; // 类名
	private String packageName; // 包名
	private String documentation; // 类上的注释信息,暂时以文本存储
	private int modifier; // 修饰符

	/*
	 * 
	 */
	private Map<String, ClassDefinition> superInterfaces; // 父接口
	private Map<String, ClassDefinition> superClasses; // 父类
	private Map<String, ClassDefinition> declaredAnnotations; // 类上的注解
	private Set<String> importList; // 导入列表

	/**
	 * 
	 */
	private ClassDefinition outerClassDefinition;
	private List<ClassDefinition> innerClassDefinitions;
	private List<FieldDefinition> fieldDefinitions;
	private List<MethodDefinition> methodDefinitions;

	public ClassDefinition() {
		superInterfaces = new HashMap<>();
		superClasses = new HashMap<>(1);
		declaredAnnotations = new HashMap<>();
		importList = new HashSet<>();
		innerClassDefinitions = new ArrayList<>();
		fieldDefinitions = new ArrayList<>();
		methodDefinitions = new ArrayList<>();
	}

	public boolean isInner() {
		return isInner;
	}

	public void setInner(boolean isInner) {
		this.isInner = isInner;
	}

	public int getClassType() {
		return classType;
	}

	public void setClassType(int classType) {
		this.classType = classType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public Map<String, ClassDefinition> getSuperInterfaces() {
		return superInterfaces;
	}

	public void setSuperInterfaces(Map<String, ClassDefinition> superInterfaces) {
		this.superInterfaces = superInterfaces;
	}

	public Map<String, ClassDefinition> getSuperClasses() {
		return superClasses;
	}

	public void setSuperClasses(Map<String, ClassDefinition> superClasses) {
		this.superClasses = superClasses;
	}

	public Map<String, ClassDefinition> getDeclaredAnnotations() {
		return declaredAnnotations;
	}

	public void setDeclaredAnnotations(Map<String, ClassDefinition> declaredAnnotations) {
		this.declaredAnnotations = declaredAnnotations;
	}

	public Set<String> getImportList() {
		return importList;
	}

	public void setImportList(Set<String> importList) {
		this.importList = importList;
	}

	public ClassDefinition getOuterClassDefinition() {
		return outerClassDefinition;
	}

	public void setOuterClassDefinition(ClassDefinition outerClassDefinition) {
		this.outerClassDefinition = outerClassDefinition;
	}

	public List<ClassDefinition> getInnerClassDefinitions() {
		return innerClassDefinitions;
	}

	public void setInnerClassDefinitions(List<ClassDefinition> innerClassDefinitions) {
		this.innerClassDefinitions = innerClassDefinitions;
	}

	public List<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}

	public List<MethodDefinition> getMethodDefinitions() {
		return methodDefinitions;
	}

	public void setMethodDefinitions(List<MethodDefinition> methodDefinitions) {
		this.methodDefinitions = methodDefinitions;
	}
	
	@Override
	public int compareTo(ClassDefinition o) {
		return className.compareTo(o.className);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	//=====================================================================

	public void addSuperClass(Class<?> clazz) {
		this.superClasses.put(clazz.getSimpleName(), parse(clazz));
	}
	
	public void addSuperInterface(Class<?> clazz) {
		this.superInterfaces.put(clazz.getSimpleName(), parse(clazz));
	}
	
	public static ClassDefinition parse(Class<?> clazz) {
		if (clazz == null) {
			ClassDefinition classDefinition = new ClassDefinition();
			classDefinition.setClassName("null");
			return classDefinition;
		}
		if (clazz == Object.class) {
			ClassDefinition classDefinition = new ClassDefinition();
			classDefinition.setClassName("Object");
			classDefinition.setInner(false);
			return classDefinition;
		}
		if (clazz.isInterface()) {

		}

		ClassDefinition classDefinition = new ClassDefinition();
		classDefinition.setClassName(clazz.getName());
		classDefinition.setClassType(0);
		Package package1 = clazz.getPackage();
		if (package1 != null) {
			classDefinition.setPackageName(package1.getName());
		}

		// find super class recursivly
		Class<?> clazz1 = clazz.getSuperclass();
		if (clazz1 != null) {
			classDefinition.superClasses.put(clazz1.getSimpleName(), parse(clazz1));
		} else {
			System.out.println(clazz + " getSuperclass == null");
		}

		// find super interface recursivly
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> interfaceClass : interfaces) {
			classDefinition.superInterfaces.put(interfaceClass.getSimpleName(), parse(interfaceClass));
		}

		// find super antotation recursivly
		Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
		for (Annotation annotation : declaredAnnotations) {
			classDefinition.declaredAnnotations.put(annotation.getClass().getName(), parse(annotation.getClass()));
		}
		return classDefinition;
	}

	public static void main(String[] args) {
		ClassDefinition definition = parse(TestClass.class);

		System.out.println(definition.className);
	}

	public static ClassDefinition parseObject() {
		ClassDefinition definition = new ClassDefinition();
		definition.setClassName("java.lang.Object");
		definition.setClassType(1);
		return definition;
	}
}
