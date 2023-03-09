package org.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @NotNull
    private String name;

    @Min(value = 0, message = "年龄不能为负数！")
    private int age;

    @Min(value = 0, message = "薪酬不能为负数！")
    private float salary;

    private LocalDate birthday;
}
