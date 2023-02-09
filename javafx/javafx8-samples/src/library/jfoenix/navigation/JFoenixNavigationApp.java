package library.jfoenix.navigation;

import application.SampleApplication;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import library.jfoenix.navigation.main.ColorChangeCallback;
import utils.FXColor;
import utils.FXStyle;

public class JFoenixNavigationApp extends Application {

    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     *
     * @return
     * @throws Exception
     */
    public Parent createRoot() throws Exception {
        AnchorPane root = new AnchorPane();

        JFXDrawer drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(200.0);
        root.getChildren().add(drawer);

        drawer.setLayoutX(-6.0);
        drawer.setPrefHeight(368.0);
        drawer.setPrefWidth(108.0);

        JFXHamburger hamburger = new JFXHamburger();
        root.getChildren().add(hamburger);

        hamburger.setLayoutX(460.0);
        hamburger.setLayoutY(14.0);


        VBox vBox = new VBox();


        drawer.setSidePane(vBox);
        vBox.setPrefHeight(400.0);
        vBox.setPrefWidth(178.0);

        ImageView imageView = new ImageView("/library/jfoenix/navigation/res/back.jpg");
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        ColorChangeCallback callback = new ColorChangeCallback() {
            @Override
            public void updateColor(String newColor) {
                root.setStyle("-fx-background-color:" + newColor);
            }
        };


        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFXButton btn = (JFXButton) event.getSource();

                switch (btn.getText()) {
                    case "Color 1":
                        callback.updateColor("#00FF00");
                        break;
                    case "Color 2":
                        callback.updateColor("#0000FF");
                        break;
                    case "Color 3":
                        callback.updateColor("#FF0000");
                        break;
                }
            }
        };

        JFXButton b1 = new JFXButton("Color 1");
        b1.setOnAction(handler);
        JFXButton b2 = new JFXButton("Color 2");
        b2.setOnAction(handler);
        JFXButton b3 = new JFXButton("Color 3");
        b3.setOnAction(handler);
        vBox.getChildren().addAll(imageView, b1, b2, b3);




        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

        return root;
    }

    public static void main(String[] args) {
        Application.launch(JFoenixNavigationApp.class, args);
    }

}
