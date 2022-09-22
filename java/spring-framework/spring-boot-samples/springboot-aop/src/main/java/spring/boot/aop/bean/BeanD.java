package spring.boot.aop.bean;

//@Component
public class BeanD {

    private final BeanC beanC;

    public BeanD(BeanC beanC) {
        this.beanC = beanC;
    }
}
