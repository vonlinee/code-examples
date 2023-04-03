package io.devpl.toolkit.dto;

import lombok.Data;

@Data
public class GenDtoFromSqlReq {

    /**
     * Base64编码后的SQL
     */
    private String sql;

    private GenConfigDTO config;
}
