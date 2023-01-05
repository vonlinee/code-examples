package io.devpl.toolkit.framework.mvc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * AbstractFxmlPanelController is the abstract base class for all the
 * panel controller which build their UI components from an FXML file.
 * <p>
 * Subclasses should provide a {@link AbstractFxmlPanelController#controllerDidLoadFxml() }
 * method in charge of finishing the initialization of the UI components
 * loaded from the FXML file.
 */
public abstract class AbstractFxmlPanelController extends AbstractPanelController {

    private final URL fxmlURL;
    private final ResourceBundle resources;

    /**
     * Base constructor for invocation by the subclasses.
     * @param fxmlURL the URL of the FXML file to be loaded (cannot be null)
     */
    protected AbstractFxmlPanelController(URL fxmlURL, ResourceBundle resources) {
        this.fxmlURL = fxmlURL;
        this.resources = resources;
        assert fxmlURL != null : "Check the name of the FXML file used by " + getClass().getSimpleName();
    }

    /*
     * AbstractPanelController
     */

    /**
     * This implementation loads the FXML file using the URL passed to
     * {@link AbstractFxmlPanelController}.
     * Subclass implementation should make sure that this method can be invoked
     * outside of the JavaFX thread
     */
    @Override
    protected void makePanel() {
        final FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(fxmlURL);
        loader.setResources(resources);
        try {
            setPanelRoot((Parent) loader.load());
            controllerDidLoadFxml();
        } catch (RuntimeException | IOException x) {
            System.out.println("loader.getController()=" + loader.getController());
            System.out.println("loader.getLocation()=" + loader.getLocation());
            throw new RuntimeException("Failed to load " + fxmlURL.getFile(), x); // NOI18N
        }
    }

    /*
     * Protected
     */

    /**
     * Called by {@link AbstractFxmlPanelController#makePanel() } after
     * the FXML file has been successfully loaded.
     * Warning : this routine may be invoked outside of the event thread.
     */
    protected abstract void controllerDidLoadFxml();

    // Note : remember that here:
    // 1) getHost() might be null
    // 2) getPanelRoot().getScene() might be null
    // 3) getPanelRoot().getScene().getWindow() might be null
}
