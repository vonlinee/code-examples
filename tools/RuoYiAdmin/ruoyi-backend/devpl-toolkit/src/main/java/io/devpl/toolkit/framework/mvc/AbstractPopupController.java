package io.devpl.toolkit.framework.mvc;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.transform.Transform;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public abstract class AbstractPopupController {

    private Parent root;
    private Popup popup;
    private Node anchor;
    private Window anchorWindow;

    /**
     * Returns the root FX object of this popup.
     * When called the first time, this method invokes {@link #makeRoot()}
     * to build the FX components of the panel.
     * @return the root object of the panel (never null)
     */
    public Parent getRoot() {
        if (root == null) {
            makeRoot();
            assert root != null;
        }

        return root;
    }

    public Popup getPopup() {
        assert Platform.isFxApplicationThread();

        if (popup == null) {
            popup = new Popup();
            popup.getContent().add(getRoot());
            popup.setOnHidden(onHiddenHandler);
            controllerDidCreatePopup();
        }

        return popup;
    }

    public void openWindow(Node anchor) {
        assert Platform.isFxApplicationThread();
        assert anchor != null;
        assert anchor.getScene() != null;
        assert anchor.getScene().getWindow() != null;

        this.anchor = anchor;
        this.anchorWindow = anchor.getScene().getWindow();

        this.anchor.layoutBoundsProperty().addListener(layoutBoundsListener);
        this.anchor.localToSceneTransformProperty().addListener(localToSceneTransformListener);
        this.anchorWindow.xProperty().addListener(xyListener);

        getPopup().show(this.anchor.getScene().getWindow());
        anchorBoundsDidChange();
        updatePopupLocation();
    }

    public void closeWindow() {
        assert Platform.isFxApplicationThread();
        getPopup().hide();
        // Note : Popup.hide() will invoke onHiddenHandler() which 
        // will remove listeners set by openWindow.
    }

    public boolean isWindowOpened() {
        return (popup == null) ? false : popup.isShowing();
    }

    public Node getAnchor() {
        return anchor;
    }


    /*
     * To be implemented by subclasses
     */

    /**
     * Creates the FX object composing the panel.
     * This routine is called by {@link AbstractPopupController#getRoot}.
     * It *must* invoke {@link AbstractPanelController#setPanelRoot}.
     */
    protected abstract void makeRoot();

    protected abstract void onHidden(WindowEvent event);

    protected abstract void anchorBoundsDidChange();

    protected abstract void anchorTransformDidChange();

    protected abstract void anchorXYDidChange();

    protected void controllerDidCreatePopup() {
        assert getRoot() != null;
        assert getRoot().getScene() != null;
    }

    protected abstract void updatePopupLocation();


    /*
     * For subclasses
     */

    /**
     * Set the root of this popup controller.
     * This routine must be invoked by subclass's makeRoot() routine.
     * @param panelRoot the root panel (non null).
     */
    protected final void setRoot(Parent panelRoot) {
        assert panelRoot != null;
        this.root = panelRoot;
    }

    /*
     * Private
     */

    private final ChangeListener<Bounds> layoutBoundsListener = (ov, t, t1) -> anchorBoundsDidChange();

    private final ChangeListener<Transform> localToSceneTransformListener = (ov, t, t1) -> anchorTransformDidChange();

    private final ChangeListener<Number> xyListener = (ov, t, t1) -> anchorXYDidChange();

    private final EventHandler<WindowEvent> onHiddenHandler = e -> {
        assert anchor != null;

        onHidden(e);

        anchor.layoutBoundsProperty().removeListener(layoutBoundsListener);
        anchor.localToSceneTransformProperty().removeListener(localToSceneTransformListener);
        anchorWindow.xProperty().removeListener(xyListener);

        anchor = null;
        anchorWindow = null;
    };
}
