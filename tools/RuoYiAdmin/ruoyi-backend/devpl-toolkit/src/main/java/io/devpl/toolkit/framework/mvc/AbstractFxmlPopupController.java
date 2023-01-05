package io.devpl.toolkit.framework.mvc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;

public abstract class AbstractFxmlPopupController extends AbstractPopupController {

    private final URL fxmlURL;
    private final ResourceBundle resources;

    public AbstractFxmlPopupController(URL fxmlURL) {
        this(fxmlURL, null);
    }

    public AbstractFxmlPopupController(URL fxmlURL, ResourceBundle resources) {
        assert fxmlURL != null : "Check fxml path given to " + getClass().getSimpleName();
        this.fxmlURL = fxmlURL;
        this.resources = resources;
    }

    public URL getFXMLURL() {
        return fxmlURL;
    }

    public ResourceBundle getResources() {
        return resources;
    }

    /*
     * To be implemented by subclasses
     */

    protected void controllerDidLoadFxml() {
        assert getRoot() != null;
        assert getRoot().getScene() == null;
    }

    /*
     * AbstractWindowController
     */

    /**
     * This implementation loads the FXML file using the URL passed to
     * {@link AbstractFxmlWindowController}.
     */
    @Override
    protected void makeRoot() {
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(fxmlURL);
        loader.setResources(resources);
        try {
            setRoot(loader.load());
            controllerDidLoadFxml();
        } catch (RuntimeException | IOException x) {
            System.out.println("loader.getController()=" + loader.getController());
            System.out.println("loader.getLocation()=" + loader.getLocation());
            throw new RuntimeException("Failed to load " + fxmlURL.getFile(), x); // NOI18N
        }
    }
}
