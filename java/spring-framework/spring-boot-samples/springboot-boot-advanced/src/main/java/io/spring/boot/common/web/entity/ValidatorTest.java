package io.spring.boot.common.web.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 测试Validator Bean验证的
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年4月14日
 */
public class ValidatorTest {

    // message 直接提供错误信息
    @NotNull(message = "username 不能为空")
    // message 使用 {} 代表错误内容，从 resources 目录下的 ValidationMessages.properties 文件中读取
    @Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "{user.username.illegal}")
    private String username;

    @Size(min = 5, max = 10, message = "{password.length.illegal}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
