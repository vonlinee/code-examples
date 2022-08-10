package io.maker.codegen.core.lang.meta;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * Java的类定义：用于FreeMarker的模板文件映射
 */
@Data
public class ClassDefinition implements Serializable, Comparable<ClassDefinition> {

    private static final long serialVersionUID = 1L;

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
     * inherit metadata
     */
    private Map<String, String> superInterfaces; // 父接口
    private Map<String, String> superClasses; // 父类只有1个
    private Map<String, String> declaredAnnotations; // 类上的注解

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

    @Override
    public int compareTo(ClassDefinition o) {
        return 0;
    }
}
