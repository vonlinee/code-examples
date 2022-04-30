package io.maker.base.lang.meta;

import java.io.Serializable;
import java.util.Map;

public class MethodDefinition implements Serializable {

    private int modifiers;
    private String methodName;
    private Map<String, ClassDefinition> annotations;
}
