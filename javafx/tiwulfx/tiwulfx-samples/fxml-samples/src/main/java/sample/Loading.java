package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.BiConsumer;

/**
 * Loading组件
 */
public class Loading extends StackPane {

    Region region;

    public void show() {
        getChildren().add(region = loadingDefault());
    }

    public void hide() {
        getChildren().remove(region);
        region = null;
    }

    public static Region loadingDefault() {
        ImageView imageView = new ImageView("loading.gif");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        VBox indicatorContainer = new VBox(imageView, new Label("Loading"));
        indicatorContainer.setAlignment(Pos.CENTER);

        // #4093ff50 后面两位为透明度
        final BackgroundFill bgFill = new BackgroundFill(Color.web("#4093ff50"), new CornerRadii(0), new Insets(0));
        final Background background = new Background(bgFill);
        indicatorContainer.setBackground(background);

        indicatorContainer.setPrefSize(400, 400);
        return indicatorContainer;
    }

    public static <T extends Region, P extends Pane> Loading wrap(T region, P parent, BiConsumer<Loading, P> consumer) {
        Loading loading = new Loading();
        consumer.accept(loading, parent);
        return loading;
    }
}
