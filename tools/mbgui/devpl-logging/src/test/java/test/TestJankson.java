package test;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.SyntaxError;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class TestJankson {

    public static void main(String[] args) {
        try {

            final URL resource = Thread.currentThread().getContextClassLoader().getResource("test.json");

            assert resource != null;
            JsonObject configObject = Jankson
                    .builder()
                    .build()
                    .load(new File(resource.toURI()));

            //This will strip comments and regularize the file, but emit newlines and indents for readability
            String processed = configObject.toJson(false, true);

            //This will inject a default setting after the last listed key in the object, if it doesn't already exist.
            //Otherwise it does nothing to the comment, ordering, or value.
            configObject.putDefault("someConfigKey", new JsonPrimitive(Boolean.TRUE), "Turns on the someConfigKey thing (default=TRUE)");

            System.out.println(processed);
        } catch (IOException ex) {
            // log.error("Couldn't read the config file", ex);
            //or System.exit(-1) or rethrow an exception
            ex.printStackTrace();
        } catch (SyntaxError error) {
            //or System.exit(-1) or rethrow an exception
            System.out.println(error.getCompleteMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
