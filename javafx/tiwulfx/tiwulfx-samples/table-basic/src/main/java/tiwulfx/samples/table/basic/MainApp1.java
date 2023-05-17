package tiwulfx.samples.table.basic;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.table.TableControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import tiwulfx.samples.shared.DataGenerator;
import tiwulfx.samples.shared.pojo.Person;

public class MainApp1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DataGenerator.createWithTestData(1000);
        TiwulFXUtil.addLiteralBundle("tiwulfx.samples.shared.translation");

        TableControl<Person> table = new TableControl<>(Person.class);

        Scene scene = new Scene(table);
        TiwulFXUtil.setTiwulFXStyleSheet(scene);
        stage.setTitle("Basic TableControl");
        stage.setScene(scene);
        stage.show();
    }
}
