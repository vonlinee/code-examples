package org.setamv.shardingsphere.sample.dynamic.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.setamv.shardingsphere.sample.dynamic.config.DataSourceNames;
import org.setamv.shardingsphere.sample.dynamic.config.DynamicRouteDataSourceContext;
import org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm.YearMonth;
import org.setamv.shardingsphere.sample.dynamic.spel.DynamicDataSourceRouter;
import org.setamv.shardingsphere.sample.dynamic.spel.DynamicRouteExpressionEvaluator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Controller切面
 *
 * @author setamv
 * @date 2021-04-17
 */
@Component
@Aspect
public class ServiceAspect implements BeanFactoryAware {

    public static final int OPERATE_TIME_SHARDING_UPPER_BOUND = 2021;

    @Autowired
    private DynamicRouteExpressionEvaluator dynamicRouteExpressionEvaluator;

    private BeanFactory beanFactory;

    /**
     * 方法切面
     */
    @Pointcut("@annotation(org.setamv.shardingsphere.sample.dynamic.spel.DynamicDataSourceRouter) && execution(* org.setamv.shardingsphere..service.*.*(..))")
    public void dynamicDataSourceRouterMethod() {
        // 纯声明，没有方法体
    }

    /**
     * 切面动作
     * @param joinPoint
     */
    @Around("dynamicDataSourceRouterMethod()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 保存原来的数据源
        String originDataSource = DynamicRouteDataSourceContext.getDataSource();

        determineDynamicDataSource(joinPoint);

        try {
            joinPoint.proceed();
        } finally {
            DynamicRouteDataSourceContext.setDataSource(originDataSource);
        }
    }

    /**
     * 动态路由决策
     * @param joinPoint 切面连接点
     */
    private void determineDynamicDataSource(JoinPoint joinPoint) {
        // 获取
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method rawMethod = methodSignature.getMethod();
        Method method = BridgeMethodResolver.findBridgedMethod(rawMethod);
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method targetMethod = (!Proxy.isProxyClass(targetClass) ? AopUtils.getMostSpecificMethod(method, targetClass) : method);
        EvaluationContext evaluationContext = dynamicRouteExpressionEvaluator.createEvaluationContext(
                method, joinPoint.getArgs(), joinPoint.getTarget(), targetClass, targetMethod, beanFactory);

        DynamicDataSourceRouter ann = method.getAnnotation(DynamicDataSourceRouter.class);
        String[] expressionPaths = ann.path();
        if (expressionPaths == null || expressionPaths.length == 0) {
            return;
        }
        Object startDate = dynamicRouteExpressionEvaluator.evaluateExpression(evaluationContext, expressionPaths[0]);
        Object endDate = expressionPaths.length > 1 ? dynamicRouteExpressionEvaluator.evaluateExpression(evaluationContext, expressionPaths[1]) : null;

        YearMonth startYearMonth = YearMonth.parse(startDate);
        YearMonth endYearMonth = endDate == null ? null : YearMonth.parse(endDate);
        // 根据操作日志的操作时间，做动态路由
        if (endYearMonth == null) {
            routeDataSource(startYearMonth.getYear());
        } else {
            routeDataSource(startYearMonth.getYear(), endYearMonth.getYear());
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 根据操作日志的操作时间，做动态路由：
     * <p>如果操作时间为2021年及以后，路由到SCM主库，否则，路由到分片库
     * @param year 操作日志的年份
     */
    public void routeDataSource(int year) {
        if (year >= OPERATE_TIME_SHARDING_UPPER_BOUND) {
            DynamicRouteDataSourceContext.setDataSource(DataSourceNames.SCM_DATA_SOURCE);
        }
    }

    /**
     * 根据操作日志的操作时间范围，做动态路由：
     * <p>如果操作时间为2021年及以后，路由到SCM主库，否则，路由到分片库
     * @param startYear 操作日志的起始年份，不能为空
     * @param endYear 操作日志的截止年份，不能为空
     */
    public void routeDataSource(int startYear, int endYear) {
        if (startYear > endYear) {
            throw new IllegalArgumentException("操作日志的起始操作时间不能晚于截止操作时间");
        }
        boolean startOperateTimeExceedsUpperBound = startYear >= OPERATE_TIME_SHARDING_UPPER_BOUND;
        boolean endOperateTimeExceedsUpperBound = endYear >= OPERATE_TIME_SHARDING_UPPER_BOUND;
        if (!startOperateTimeExceedsUpperBound && endOperateTimeExceedsUpperBound) {
            throw new IllegalArgumentException("操作日期的起始操作时间和截止操作时间不能跨越2021年");
        }
        if (startOperateTimeExceedsUpperBound) {
            // 切换数据源
            DynamicRouteDataSourceContext.setDataSource(DataSourceNames.SCM_DATA_SOURCE);

            // 开启一个新的事务，并指定RequiredNew
//            startNewReadonlyTransaction();
        }
    }

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    public TransactionStatus startNewReadonlyTransaction() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        definition.setReadOnly(true);
        return platformTransactionManager.getTransaction(definition);
    }
}
