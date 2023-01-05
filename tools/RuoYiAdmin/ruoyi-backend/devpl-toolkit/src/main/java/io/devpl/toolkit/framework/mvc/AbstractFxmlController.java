package io.devpl.toolkit.framework.mvc;

import java.io.IOException;
import java.net.URL;

import io.devpl.toolkit.framework.utils.I18N;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * AbstractFxmlController is the abstract base class for all the
 * controller which build their UI components from an FXML file.
 * <p>
 * Subclasses should provide a {@link AbstractFxmlPanelController#controllerDidLoadFxml() }
 * method in charge of finishing the initialization of the UI components
 * loaded from the FXML file.
 */
public abstract class AbstractFxmlController extends AbstractPanelController {

    private final URL fxmlURL;

    /**
     * Base constructor for invocation by the subclasses.
     * @param fxmlURL the URL of the FXML file to be loaded (cannot be null)
     */
    protected AbstractFxmlController(URL fxmlURL) {
        this.fxmlURL = fxmlURL;
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
        loader.setResources(I18N.getBundle());
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

    @Override
    protected void sceneGraphRevisionDidChange() {
        // Ignored
    }

    @Override
    protected void cssRevisionDidChange() {
        // Ignored
    }

    @Override
    protected void jobManagerRevisionDidChange() {
        // Ignored
    }

    @Override
    protected void editorSelectionDidChange() {
        // Ignored
    }
}
