package sample.spring.ioc.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA {

//    private CircularDependencyB circB;

//    @Autowired
//    public CircularDependencyA(CircularDependencyB circB) {
//        this.circB = circB;
//    }
}