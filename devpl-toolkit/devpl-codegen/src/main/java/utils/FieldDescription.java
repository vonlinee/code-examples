package utils;

import lombok.Data;

@Data
public class FieldDescription {

    private String name;

    private String comment;

    /**
     * 类型名称
     */
    private String typeName;
}
