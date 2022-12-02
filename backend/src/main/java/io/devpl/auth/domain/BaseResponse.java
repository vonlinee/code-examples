package io.devpl.auth.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * 基础平台接口响应
 */
@Data
public class BaseResponse {

    @JsonAlias("StatusCode")
    private Integer statusCode;

    @JsonAlias("ErrCode")
    private Integer errCode;

    // 提示信息，success表示成功
    @JsonAlias("Msg")
    private String msg;

}
