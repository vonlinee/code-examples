package io.devpl.fxui.mvvm;

import javafx.fxml.Initializable;
import javafx.scene.Node;

public abstract class FxmlView extends ViewBase implements Initializable {

    @Override
    public final void setRoot(Node root) {
        super.setRoot(root);
    }
}
