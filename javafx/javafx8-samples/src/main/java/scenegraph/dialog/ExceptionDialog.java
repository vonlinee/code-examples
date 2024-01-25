package scenegraph.dialog;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 单例
 */
public class ExceptionDialog extends Stage {

    private static final ExceptionDialog dialog = new ExceptionDialog();

    private final CodeArea codeArea = new CodeArea();

    private ExceptionDialog() {
        initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(codeArea, 400.0, 400.0);
        setScene(scene);
    }

    public void setText(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        codeArea.appendText(stringWriter.toString());
    }

    public static void report(Throwable throwable) {
        dialog.setText(throwable);
        if (!dialog.isShowing()) {
            dialog.centerOnScreen();
            dialog.show();
        }
    }
}