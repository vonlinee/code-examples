import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MyApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Tweet> tweets = new ArrayList<>();

        Tweet tweet = new Tweet();
        tweet.text = "AI won’t replace you. But someone using AI might. Time to level up!";
        tweet.time = new Timestamp(System.currentTimeMillis());
        tweets.add(tweet);
        Thread.sleep(3);
        tweet = new Tweet();
        tweet.text = "Building a startup is like jumping off a cliff and assembling the plane on the way down.";
        tweet.time = new Timestamp(System.currentTimeMillis());
        tweets.add(tweet);

        String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tweets);
        System.out.println(s);

        tweets.forEach(t -> System.out.format("%n%s: %s%n", t.getTime(), t.getText()));
    }
}