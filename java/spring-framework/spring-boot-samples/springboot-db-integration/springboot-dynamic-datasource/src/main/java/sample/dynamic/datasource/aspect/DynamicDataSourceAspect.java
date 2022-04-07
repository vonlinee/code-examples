package sample.dynamic.datasource.aspect;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.Ordered;

// @Component
// @Aspect
public class DynamicDataSourceAspect implements Ordered {

    // 前置
    // @Before("within(com.tuling.dynamic.datasource.service.impl.*) && @annotation(wr)")
    public void before(JoinPoint point) {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
