package sample.spring.aop.cglib;

import net.sf.cglib.proxy.FixedValue;

public class TargetResultFixed implements FixedValue {
    @Override
    public Object loadObject() throws Exception {
        return 999;
    }
}