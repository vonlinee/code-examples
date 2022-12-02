package io.devpl.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键
    private String authorityCode;

    private String authorityName;

    // 权限类型，预留字段
    private String authorityType;

    private Timestamp createTime;

    private Timestamp updateTime;
}
