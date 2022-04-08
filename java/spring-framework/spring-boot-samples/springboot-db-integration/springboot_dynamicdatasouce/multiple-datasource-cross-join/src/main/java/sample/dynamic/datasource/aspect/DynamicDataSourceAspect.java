package sample.dynamic.datasource.aspect;

import sample.dynamic.datasource.config.DataSourceDecision;
import sample.dynamic.datasource.config.DynamicDataSource;
import sample.dynamic.datasource.annotation.WR;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DynamicDataSourceAspect implements Ordered {

    // 前置
    @Before("within(sample.dynamic.datasource.service.impl.*) && @annotation(wr)")
    public void before(JoinPoint point, WR wr) {
        String name = wr.value();
        DataSourceDecision.decide(name);
        System.out.println(name);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
