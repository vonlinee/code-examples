package io.devpl.toolkit.dto;

import lombok.Data;

@Data
public class GenDtoFromSqlReq {

    private String sql;

    private GenDtoConfig config = new GenDtoConfig();

}
