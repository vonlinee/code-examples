package io.devpl.auth.domain;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户列表查询参数，目前先固定，后续根据需要扩展
 */
@Data
public class UserListParam {

    // 当前页码
    @Min(value = 1, message = "页码不能小于1")
    private int pageNum = 1;

    // 每页记录数，0查询所有
    @Min(value = 0, message = "每页记录数不能小于0")
    private int pageSize = 10;

    // 用户身份
//    @NotBlank(message = "用户身份不能为空")
//    private String identityCode;

    // 用户类型
    @NotNull(message = "用户类型不能为空")
    private UserType userType;

    // 模糊查询，根据机构名称
    private String keyword;

    // true关联的机构审核通过
    private boolean verified;

    public void setUserType(String userType) {
        if (StringUtils.hasText(userType)) {
            try {
                this.userType = UserType.valueOf(userType.toUpperCase());
            } catch (Exception exception) {
                throw new IllegalArgumentException("不支持的用户类型");
            }
        }
    }

}
