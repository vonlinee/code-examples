package de.saxsys.mvvmfx.core;

import de.saxsys.mvvmfx.fxml.FxmlControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ResourceBundle;

public class FxmlViewComponent extends ViewComponent {

    private final String fxmlId;
    private final URL fxmlLocation;
    private final ResourceBundle resourceBundle;

    FxmlControllerFactory fxmlViewControllerFactory;

    public FxmlViewComponent(String fxmlId, URL fxmlLocation, ResourceBundle resourceBundle, FxmlControllerFactory fxmlViewControllerFactory) {
        this.fxmlId = fxmlId;
        this.fxmlLocation = fxmlLocation;
        this.resourceBundle = resourceBundle;
        this.fxmlViewControllerFactory = fxmlViewControllerFactory;
    }

    @Override
    View createView() {
        refresh();
        return viewRef.get();
    }

    @Override
    Parent createRoot() {
        refresh();
        return viewRootRef.get();
    }

    private void refresh() {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        fxmlViewControllerFactory.setCurrentLoadingFxmlId(fxmlId);
        loader.setControllerFactory(fxmlViewControllerFactory);
        Object root, controller;
        try {
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!(root instanceof Parent)) {
            throw new RuntimeException("the root node of " + fxmlLocation + " is not a Parent!");
        }
        if (!(controller instanceof FxmlView)) {
            throw new RuntimeException("the controller of " + fxmlLocation + " is not a FxmlView!");
        }
        this.viewRootRef = new SoftReference<>((Parent) root);
        this.viewRef = new SoftReference<>((FxmlView<?>) controller);
    }

    public String getFxmlId() {
        return fxmlId;
    }

    public URL getFxmlLocation() {
        return fxmlLocation;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
