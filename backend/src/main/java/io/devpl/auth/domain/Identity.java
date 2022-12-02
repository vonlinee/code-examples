package io.devpl.auth.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 身份/角色
 */
@Data
@Table(name = "user_identity")
public class Identity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 机构用户身份代码
    // public static final String AGENCY_CODE = "IC0201";
    // 管理员身份代码（与基础平台相同），可分配任务
    // public static final String ADMIN_CODE = "IC0101";
    // 审查员身份代码（对应基础平台教育局领导）
    // public static final String REVIEWER_CODE = "IC0102";

    // 身份代码，主键
    private String identityCode;

    // 身份名称
    private String identityName;

    // 身份描述
    private String description;

    // 身份标志图
    private String iconUrl;

    // 是否系统预设身份
    private boolean present;

    private Timestamp createTime;

    private Timestamp updateTime;

    // 非数据库字段，记录身份identity下拥有的权限authority
    private List<Authority> authorityList;
}
