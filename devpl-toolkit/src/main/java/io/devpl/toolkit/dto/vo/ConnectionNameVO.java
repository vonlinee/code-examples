package io.devpl.toolkit.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionNameVO {

    /**
     * 连接ID
     */
    private String connectionId;

    /**
     * 连接名称
     */
    private String connectionName;
}
