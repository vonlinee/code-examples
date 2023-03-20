package test;

import org.hjson.JsonValue;
import org.hjson.Stringify;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class TestJson {

    public static void main(String[] args) throws IOException, URISyntaxException {

        URL resource = Thread.currentThread().getContextClassLoader().getResource("test.json");

        File file = new File(resource.toURI());

        try (FileReader fileReader = new FileReader(file)){
            String hjsonString = JsonValue.readHjson(fileReader).toString(Stringify.HJSON);
            System.out.println(hjsonString);
        }
    }
}
