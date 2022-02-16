package code.magicode.generator.lang.java;

import java.io.Serializable;
import java.lang.reflect.Modifier;

public class JavaClassDefinition implements Serializable {

    private static final String EXTENDS = "extends";
    private static final String IMPLEMENTS = "implements";

    private String className;
    private Modifier modifier;
    private Class<?>[] interfaces;
    private Class<?> superClass;

    private JavaClassField<?>[] fieldDefinitions;
    private JavaMethodDefinition[] methodDefinitions;

}
