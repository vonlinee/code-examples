package sample.spring.aop.xml.afterreturning;

public class Performer {
    public int perform() {
        System.out.println("PERFORMER INVOKED");
        return 78;
    }
}
