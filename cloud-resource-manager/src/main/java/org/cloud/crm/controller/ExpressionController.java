package org.cloud.crm.controller;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/el")
public class ExpressionController {

    List<Expression> expressions = new ArrayList<>();

    /**
     * @param expression 例如：hello world".subString(5,6)
     */
    @GetMapping("/add")
    public void addExpression(String expression) {
        if (expression == null) {
            expression = "2>1 && (NOT true || NOT false)";
        }

        ExpressionParser expressionParser = new SpelExpressionParser();// 指定spelExpressionParser解析器实现类
        Expression el = expressionParser.parseExpression(expression);//解析表达式
        expressions.add(el);
    }
}
