package tiwulfx.samples.table.basic;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.utils.SceneGraph;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiwulfx.samples.shared.DataGenerator;
import tiwulfx.samples.shared.pojo.Person;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DataGenerator.createWithTestData(1000);
        TiwulFXUtil.addLiteralBundle("tiwulfx.samples.shared.translation");
        final FrmPerson frmPerson = new FrmPerson();
        frmPerson.reload();

        ObservableList<String> list = FXCollections.observableArrayList();

        list.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                for (String s : c.getAddedSubList()) {
                    System.out.println(s);
                }
            }
        });

        List<String> simpleList = new ArrayList<>();

        list.setAll(simpleList);

        VBox vBox = new VBox(frmPerson, SceneGraph.button("A", event -> {
            Person selectedItem = frmPerson.tblPerson.getSelectedItem();
            System.out.println(selectedItem);

            simpleList.add("A");
            simpleList.add("B");
            simpleList.add("C");

        }));

        Scene scene = new Scene(vBox);
        TiwulFXUtil.setTiwulFXStyleSheet(scene);
        stage.setTitle("Basic TableControl");

        stage.setScene(scene);

        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be launched
     * through deployment artifacts, e.g., in IDEs with limited FX support.
     * NetBeans ignores main().
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
