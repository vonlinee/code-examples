package io.devpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.StandardELContext;
import javax.el.ValueExpression;

public class Hero {

    public static void main(String[] args) {

        ExpressionFactory factory = ExpressionFactory.newInstance();

        ELContext elContext = new StandardELContext(factory);

        ValueExpression expression = factory.createValueExpression(elContext, "", int.class);

        String expressionString = expression.getExpressionString();

        System.out.println(expressionString);
    }
}
