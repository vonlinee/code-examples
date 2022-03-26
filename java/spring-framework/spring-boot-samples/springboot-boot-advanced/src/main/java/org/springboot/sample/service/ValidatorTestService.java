package org.springboot.sample.service;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated // 告诉MethodValidationPostProcessor此Bean需要开启方法级别验证支持  
@Service
public class ValidatorTestService {
    public @Length(min = 12, max = 16, message = "返回值长度应该为12-16")
    String getContent(
            @NotBlank(message = "name不能为空")
                    String name,
            @Size(min = 5, max = 10, message = "{password.length.illegal}")
                    String password) {
        return name + ":" + password;
    }
}
