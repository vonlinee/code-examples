package code.fxutils.core.mvc.view.app;

import code.fxutils.core.mvc.view.Signal;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

@Signal(flag = true)
public class TextHandlerFrame {

    private TextArea left;
    private TextArea right;

    private BorderPane root;
    private Scene scene;

    private SplitPane splitPane;

    public TextHandlerFrame() {
        root = new BorderPane();
        scene = new Scene(root, 600, 450);
        root.
                setId("text-handle");


    }

    private void method() {

    }

}
