package io.devpl.toolkit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JavaFieldInfo {

    private String fieldName;
    private Class<?> fieldType;
    private String initialValue;
}
