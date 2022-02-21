package io.maker.generator.lang.java;

import java.io.Serializable;
import java.lang.reflect.Modifier;

/**
 * Java的类定义：用于FreeMarker的模板文件映射
 */
public class JavaClassDefinition implements Serializable {

    private static final String EXTENDS = "extends";
    private static final String IMPLEMENTS = "implements";

    private boolean isInner = false; //是否是内部类
    private String className; //类名
    private String packageName; //包名
    private String documentation; //类上的注释信息
    private Modifier modifier; //修饰符
    private Class<?>[] interfaces; //父接口
    private Class<?> superClass;  //父类
    private Class<?>[] annotations; //类上的注解
    private String[] importList; //导入列表
    private JavaClassDefinition[] innerClassDefinitions;

    private JavaClassField<?>[] fieldDefinitions;
    private JavaMethodDefinition[] methodDefinitions;

}
