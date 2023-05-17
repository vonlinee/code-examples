package tiwulfx.samples.table.basic;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.table.TableControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tiwulfx.samples.shared.DataGenerator;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DataGenerator.createWithTestData(1000);
        TiwulFXUtil.addLiteralBundle("tiwulfx.samples.shared.translation");
        final FrmPerson frmPerson = new FrmPerson();
        frmPerson.reload();

        final ToolBar toolBar = new ToolBar();

        BorderPane root = new BorderPane();
        root.setCenter(frmPerson);
        root.setTop(toolBar);

        final Button button = new Button();

        button.setOnAction(event -> {
            frmPerson.tblPerson.setVisibleComponents(false, TableControl.Component.BUTTON_EDIT);
        });
        toolBar.getItems().add(button);
        Scene scene = new Scene(root);
        TiwulFXUtil.setTiwulFXStyleSheet(scene);
        stage.setTitle("Basic TableControl");
        stage.setScene(scene);
        stage.show();
    }
}