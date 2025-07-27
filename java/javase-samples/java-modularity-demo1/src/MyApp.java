import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class MyApp {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Tweet> tweets = mapper.readValue(System.in, new TypeReference<List<Tweet>>() {
        });
        tweets.forEach(t -> System.out.format("%n%s: %s%n", t.getTime(), t.getText()));
    }
}
