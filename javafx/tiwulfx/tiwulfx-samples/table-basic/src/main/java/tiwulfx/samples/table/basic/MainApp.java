package tiwulfx.samples.table.basic;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tiwulfx.samples.shared.DataGenerator;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DataGenerator.createWithTestData(1000);
        TiwulFXUtil.addLiteralBundle("tiwulfx.samples.shared.translation");
        final FrmPerson frmPerson = new FrmPerson();
        frmPerson.reload();
        Scene scene = new Scene(frmPerson);
        TiwulFXUtil.setTiwulFXStyleSheet(scene);
        stage.setTitle("Basic TableControl");
        stage.setScene(scene);
        stage.show();
    }
}
