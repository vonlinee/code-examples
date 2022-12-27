package io.devpl.toolkit.fxui.framework.mvc;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;

/**
 * AbstractPanelController is the abstract base class for all the panel controllers
 * <p>
 * Subclasses must provide three methods:
 * <ul>
 * <li><code>makePanel</code> must create the FX components
 * which compose the panel
 * <li><code>fxomDocumentDidChange</code> must keep the panel up to date
 * after the editor controller has changed the base document
 * <li><code>editorSelectionDidChange</code> must keep the panel up to date
 * after the editor controller has changed the selected objects.
 * </ul>
 */
public abstract class AbstractPanelController {

    private Parent panelRoot;

    /**
     * Returns the root FX object of this panel.
     * When called the first time, this method invokes {@link #makePanel()}
     * to build the FX components of the panel.
     * @return the root object of the panel (never null)
     */
    public Parent getPanelRoot() {
        if (panelRoot == null) {
            makePanel();
            assert panelRoot != null;
        }
        return panelRoot;
    }

    /*
     * To be implemented by subclasses
     */

    /**
     * Creates the FX object composing the panel.
     * This routine is called by {@link AbstractPanelController#getPanelRoot}.
     * It *must* invoke {@link AbstractPanelController#setPanelRoot}.
     */
    protected abstract void makePanel();

    /**
     * Updates the panel after the revision of the scene graph has changed.
     * Revision is incremented each time the fxom document rebuilds the
     * scene graph.
     */
    protected abstract void sceneGraphRevisionDidChange();

    /**
     * Updates the panel after the css revision has changed.
     * Revision is incremented each time the fxom document forces FX to
     * reload its stylesheets.
     */
    protected abstract void cssRevisionDidChange();

    /**
     * Updates the panel after the revision of job manager has changed.
     * Revision is incremented each time a job is executed, undone or redone.
     */
    protected abstract void jobManagerRevisionDidChange();

    /**
     * Updates the panel after the editor controller has changed the selected
     * objects.
     */
    protected abstract void editorSelectionDidChange();

    /*
     * For subclasses
     */

    /**
     * Set the root of this panel controller.
     * This routine must be invoked by subclass's makePanel() routine.
     * @param panelRoot the root panel (non null).
     */
    protected final void setPanelRoot(Parent panelRoot) {
        assert panelRoot != null;
        this.panelRoot = panelRoot;
    }

    private final ChangeListener<Number> fxomDocumentRevisionListener = (observable, oldValue, newValue) -> {
        try {
            sceneGraphRevisionDidChange();
        } catch (RuntimeException x) {

        }
    };

    private final ChangeListener<Number> cssRevisionListener = (observable, oldValue, newValue) -> {
        try {
            cssRevisionDidChange();
        } catch (RuntimeException x) {

        }
    };

    private final ChangeListener<Number> jobManagerRevisionListener = (observable, oldValue, newValue) -> {
        try {
            jobManagerRevisionDidChange();
        } catch (RuntimeException x) {

        }
    };

    private final ChangeListener<Number> editorSelectionListener = (observable, oldValue, newValue) -> {
        try {
            editorSelectionDidChange();
        } catch (RuntimeException x) {

        }
    };
}
