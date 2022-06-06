package design.pattern.proxy.cglib;

import java.io.IOException;

public class TestCglibMain {

    public static void main(String[] args) throws IOException {
        RealClass realObject = (RealClass) new CglibDynamicProxy().getProxyObject(new RealClass());
        realObject.hah();
        int i = System.in.read();

        System.out.println(i);
    }
}