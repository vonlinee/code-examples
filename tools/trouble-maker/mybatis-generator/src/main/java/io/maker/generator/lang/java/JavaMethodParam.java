package io.maker.generator.lang.java;

import lombok.Data;

import java.io.Serializable;

@Data
public class JavaMethodParam implements Serializable {
    private String name;
    private String type;


}
