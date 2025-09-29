import org.example.Setter;

public class Main {

    @Setter
    static class Person {
        private String name;
        private int age;
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Foo");
        System.out.println("person.name is " + person.name);
    }
}