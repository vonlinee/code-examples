package org.example.test;

import org.example.bean.Employee;
import org.example.bean.Result;
import org.example.utils.ValueFill;

public class Main {

    public static void main(String[] args) {

        Result<Employee> result = new Result<>();

        Object defaultValueForClass = ValueFill.createDefaultValueForClass(result.getClass());

        System.out.println(defaultValueForClass);
    }
}
