package io.devpl.auth.service;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 身份列表查询参数
 * @author Xu Jiabao
 * @since 2022/3/28
 */
@Data
public class IdentityListParam {

    // 当前页码
    @Min(value = 1, message = "页码不能小于1")
    private int pageNum = 1;

    // 每页记录数，0查询所有
    @Min(value = 0, message = "每页记录数不能小于0")
    private int pageSize = 10;
}
