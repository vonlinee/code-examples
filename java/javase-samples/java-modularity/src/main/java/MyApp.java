import com.google.gson.Gson;

class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class MyApp {

    public static void main(String[] args) {

        Gson gson = new Gson();

        Person person = new Person(1, "foo");

        String json = gson.toJson(person);
    }
}
