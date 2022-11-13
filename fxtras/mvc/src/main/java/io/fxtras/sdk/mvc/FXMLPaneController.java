package io.fxtras.sdk.mvc;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class FXMLPaneController extends PaneController implements Initializable {

    private URL fxmlLocation;
    private ResourceBundle resourceBundle;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        this.fxmlLocation = location;
        this.resourceBundle = resources;
        super.setRoot(this.getRoot());
        this.initialize();
    }

    public final void reloadFxml() {
        FXMLLoader loader = new FXMLLoader(fxmlLocation, resourceBundle);
        try {
            Object root = loader.load();
            if (root instanceof Pane) {
                super.setRoot(((Pane) root));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
