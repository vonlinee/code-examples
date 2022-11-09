package sample.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bean {

    @Component
    public static class A {
        @Autowired
        public B b;
    }

    @Component
    public static class B {
        @Autowired
        public A a;
    }

    @Component
    public static class C {

        public D d;

        public C(D d) {
            this.d = d;
        }
    }

    @Component
    public static class D {

        public C c;

        public D(C c) {
            this.c = c;
        }
    }
}
