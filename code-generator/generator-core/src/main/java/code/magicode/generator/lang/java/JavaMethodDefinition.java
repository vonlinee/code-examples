package code.magicode.generator.lang.java;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.List;

public class JavaMethodDefinition implements Serializable {

    private Modifier[] modifiers;
    private String methodName;
    private List<JavaMethodParam> params;




}
