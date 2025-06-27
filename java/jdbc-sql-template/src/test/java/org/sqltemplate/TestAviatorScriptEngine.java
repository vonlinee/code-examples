package org.sqltemplate;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;

public class TestAviatorScriptEngine {

    @Test
    public void test1() {
        AviatorEvaluatorInstance instance = AviatorEvaluator.getInstance();

        Object result = instance.execute("12");

        System.out.println(result);

        AopUtils.getTargetClass();
    }
}
