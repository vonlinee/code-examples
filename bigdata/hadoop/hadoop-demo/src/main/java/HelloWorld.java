import java.net.URL;

public class HelloWorld {

    public static void main(String[] args) {
        String version = System.getProperty("java.specification.version");

        URL resource = HelloWorld.class.getResource("txt/1.txt");

        System.out.println(resource);
    }
}
