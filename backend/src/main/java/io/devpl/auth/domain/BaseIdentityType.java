package io.devpl.auth.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 身份IdentityType
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseIdentityType implements Serializable {

    // 身份代码
    @JsonAlias("IdentityCode")
    private String identityCode;

    // 身份名称
    @JsonAlias("IdentityName")
    private String identityName;

    // 身份标志图（绝对路径）
    @JsonAlias("IconUrl")  // Jackson反序列化别名
    private String iconUrl;

    // 是否系统预设身份
    @JsonAlias("IsPreset")
    private Boolean isPreset;

    // 模块权限ID串，英文逗号分隔
    @JsonAlias("ModuleIDs")
    private String moduleIds;

    // fastjson反序列化别名
    @JSONField(name = "IconUrl", alternateNames = {"ImgPath"})
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
