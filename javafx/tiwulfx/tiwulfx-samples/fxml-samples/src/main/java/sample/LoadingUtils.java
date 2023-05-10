package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class LoadingUtils {

    public static Region loadingDefault(StackPane container) {
        final URL resource = Thread.currentThread().getContextClassLoader().getResource("loading.fxml");
        Region result = null;
        try {
            assert resource != null;
            result = FXMLLoader.load(resource);
            container.getChildren().add(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean remove(StackPane container, Region region) {
        return container.getChildren().remove(region);
    }
}
