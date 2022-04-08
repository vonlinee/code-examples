package org.setamv.shardingsphere.sample.dynamic.spel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 动态路由表达式求值
 *
 * @author setamv
 * @date 2021-04-20
 */
@Component
public class DynamicRouteExpressionEvaluator {

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 创建SPEL表达式求值的上下文
     * @param method
     *          this.method = BridgeMethodResolver.findBridgedMethod(method);
     * @param args 方法的参数列表
     * @param target 目标对象
     * @param targetClass 目标对象的类
     * @param targetMethod 目标方法
     *           this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
     * 					AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
     * @param beanFactory
     * @return
     */
    public EvaluationContext createEvaluationContext(
            Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod,
            @Nullable BeanFactory beanFactory) {

        DynamicRouteExpressionRootObject rootObject = new DynamicRouteExpressionRootObject(
                method, args, target, targetClass);
        DynamicRouteEvaluationContext evaluationContext = new DynamicRouteEvaluationContext(
                rootObject, targetMethod, args, parameterNameDiscoverer);
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    public Object evaluateExpression(EvaluationContext context, String expression) {
        Expression exp = expressionParser.parseExpression(expression);
        return exp.getValue(context);
    }

    public <T> T evaluateExpression(EvaluationContext context, String expression, Class<T> clazz) {
        Expression exp = expressionParser.parseExpression(expression);
        return exp.getValue(context, clazz);
    }
}
