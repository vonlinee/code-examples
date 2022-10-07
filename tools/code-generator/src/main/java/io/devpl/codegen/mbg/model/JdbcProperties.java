package io.devpl.codegen.mbg.model;

import lombok.Data;

@Data
public class JdbcProperties {

    private String name;
    private int value;
    private String driverClass;

}
