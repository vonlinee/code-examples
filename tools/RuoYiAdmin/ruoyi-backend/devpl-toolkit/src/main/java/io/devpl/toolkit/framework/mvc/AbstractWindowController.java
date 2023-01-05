package io.devpl.toolkit.framework.mvc;

import java.util.List;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class AbstractWindowController {

    final private Stage owner;
    private Parent root;
    private Scene scene;
    private Stage stage;
    private final double CLAMP_FACTOR = 0.9;
    private final boolean sizeToScene; // true by default
    private String toolStylesheet = null;

    private final EventHandler<WindowEvent> closeRequestHandler = event -> {
        onCloseRequest(event);
        event.consume();
    };

    public AbstractWindowController() {
        this(null, true);
    }

    public AbstractWindowController(Stage owner) {
        this(owner, true);
    }

    public AbstractWindowController(Stage owner, boolean sizeToScene) {
        this.owner = owner;
        this.sizeToScene = sizeToScene;
    }

    /**
     * Returns the root FX object of this window.
     * When called the first time, this method invokes {@link #makeRoot()}
     * to build the FX components of the panel.
     * @return the root object of this window (never null)
     */
    public Parent getRoot() {
        if (root == null) {
            makeRoot();
            assert root != null;
            // toolStylesheetDidChange(null);
        }
        return root;
    }

    /**
     * Returns the scene of this window.
     * This method invokes {@link #getRoot()}.
     * When called the first time, it also invokes {@link #controllerDidCreateScene()}
     * just after creating the scene object.
     * @return the scene object of this window (never null)
     */
    public Scene getScene() {
        assert Platform.isFxApplicationThread();
        if (scene == null) {
            scene = new Scene(getRoot());
            controllerDidCreateScene();
        }
        return scene;
    }

    /**
     * Returns the stage of this window.
     * This method invokes {@link #getScene()}.
     * When called the first time, it also invokes {@link #controllerDidCreateStage()}
     * just after creating the stage object.
     * @return the stage object of this window (never null).
     */
    public Stage getStage() {
        assert Platform.isFxApplicationThread();

        if (stage == null) {
            stage = new Stage();
            stage.initOwner(owner);
            stage.setOnCloseRequest(closeRequestHandler);
            stage.setScene(getScene());
            clampWindow();
            if (sizeToScene) {
                stage.sizeToScene();
            }
            // By default we set the same icons as the owner
            if (owner != null) {
                stage.getIcons().addAll(owner.getIcons());
            }

            controllerDidCreateStage();
        }

        return stage;
    }

    /**
     * Opens this window and place it in front.
     */
    public void openWindow() {
        assert Platform.isFxApplicationThread();

        getStage().show();
        getStage().toFront();
    }

    /**
     * Closes this window.
     */
    public void closeWindow() {
        assert Platform.isFxApplicationThread();
        getStage().close();
    }

    /**
     * Returns the tool stylesheet used by this window controller.
     * @return the tool stylesheet used by this window controller.
     */
    public String getToolStylesheet() {
        return toolStylesheet;
    }

    /**
     * Sets the tool stylesheet used by this window controller.
     * @param toolStylesheet the tool stylesheet to be used by this window controller.
     */
    public void setToolStylesheet(String toolStylesheet) {
        final String oldStylesheet = this.toolStylesheet;
        this.toolStylesheet = toolStylesheet;
        if (this.root != null) {
            toolStylesheetDidChange(oldStylesheet);
        }
    }


    /*
     * To be implemented by subclasses
     */

    /**
     * Creates the FX object composing the window content.
     * This routine is called by {@link AbstractWindowController#getRoot}.
     * It *must* invoke {@link AbstractWindowController#setRoot}.
     */
    protected abstract void makeRoot();

    public abstract void onCloseRequest(WindowEvent event);

    protected void controllerDidCreateScene() {
        assert getRoot() != null;
        assert getRoot().getScene() != null;
        assert getRoot().getScene().getWindow() == null;
    }

    protected void controllerDidCreateStage() {
        assert getRoot() != null;
        assert getRoot().getScene() != null;
        assert getRoot().getScene().getWindow() != null;
    }

    /*
     * For subclasses
     */

    /**
     * Set the root of this panel controller.
     * This routine must be invoked by subclass's makePanel() routine.
     * @param root the root panel (non null).
     */
    protected final void setRoot(Parent root) {
        assert root != null;
        this.root = root;
    }

    protected void toolStylesheetDidChange(String oldStylesheet) {
        final List<String> stylesheets = root.getStylesheets();
        if (oldStylesheet != null) {
            stylesheets.remove(oldStylesheet);
        }
        stylesheets.add(toolStylesheet);
    }


    /*
     * Private
     */

    // See DTL-5928
    // The three approaches below do not provide any resizing, for some reason:
    // (1)
    //            stage.setHeight(newHeight);
    //            stage.setWidth(newWidth);
    // (2)
    //            scene.getWindow().setHeight(newHeight);
    //            scene.getWindow().setWidth(newWidth);
    // (3)
    //            getRoot().resize(newWidth, newHeight);
    //
    // The current implementation raises the point root of layout must be
    // a Region, which is for now acceptable but could perhaps be an issue later.
    private void clampWindow() {
        if (getRoot() instanceof Region) {
            Rectangle2D vBounds = Screen.getPrimary().getVisualBounds();
            double primaryScreenHeight = vBounds.getHeight();
            double primaryScreenWidth = vBounds.getWidth();
            double currentHeight = getRoot().prefHeight(-1);
            double currentWidth = getRoot().prefWidth(-1);

            if (currentHeight > primaryScreenHeight) {
                double newHeight = primaryScreenHeight * CLAMP_FACTOR;
                //            System.out.println("Clamp: new height is " + newHeight);
                assert getRoot() instanceof Region;
                ((Region) getRoot()).setPrefHeight(newHeight);
            }

            if (currentWidth > primaryScreenWidth) {
                double newWidth = primaryScreenWidth * CLAMP_FACTOR;
                //            System.out.println("Clamp: new width is " + newWidth);
                assert getRoot() instanceof Region;
                ((Region) getRoot()).setPrefWidth(newWidth);
            }
        }
    }

    protected Rectangle2D getBiggestViewableRectangle() {
        assert stage != null;

        Rectangle2D res;

        if (Screen.getScreens().size() == 1) {
            res = Screen.getPrimary().getVisualBounds();
        } else {
            Rectangle2D stageRect = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            List<Screen> screens = Screen.getScreensForRectangle(stageRect);

            // The stage is entirely rendered on one screen, which is either the
            // primary one or not, we don't care here.
//            if (screens.size() == 1) {
            res = screens.get(0).getVisualBounds();
//            } else {
            // The stage is spread over several screens.
            // We compute the surface of the stage on each on the involved
            // screen to select the biggest one == still to be implemented.
//                TreeMap<String, Screen> sortedScreens = new TreeMap<>();
//                
//                for (Screen screen : screens) {
//                    computeSurface(screen, stageRect, sortedScreens);
//                }
//                
//                res = sortedScreens.get(sortedScreens.lastKey()).getVisualBounds();
//            }
        }

        return res;
    }

    // Compute the percentage of the surface of stageRect which is rendered in
    // the given screen and write the result in sortedScreens (percentage is
    // rounded and turned into a String so that we benefit natural order sorting.
//    private void computeSurface(Screen screen, Rectangle2D stageRect, TreeMap<String, Screen> sortedScreens) {
//        Rectangle2D screenBounds = screen.getVisualBounds();
//        double surfaceX, surfaceY, surfaceW, surfaceH;
//        if (screenBounds.getMinX() < stageRect.getMinX()) {
//            if (screenBounds.getMinX() < 0) {
//                surfaceX = stageRect.getMinX();
//            } else {
//                surfaceX = screenBounds.getMinX();
//            }
//        } else {
//            
//        }
//    }
}
