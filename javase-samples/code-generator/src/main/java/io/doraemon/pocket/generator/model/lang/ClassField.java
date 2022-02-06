package io.doraemon.pocket.generator.model.lang;

import java.util.List;

public class ClassField<T> {
    String name;
    Class<T> type;
    boolean isPrimitive;
    T intialValue;
    List<String> modifiers;


}
