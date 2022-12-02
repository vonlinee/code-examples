package io.devpl.auth.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 根据身份代码获取该身份详情响应
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseIdentityResponse extends BaseResponse {

    @JsonAlias("Data")
    private List<BaseIdentityType> data;
}
