package io.spring.boot.common.web;

import io.spring.boot.common.entity.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class StudentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student order = (Student) target;
    }
}