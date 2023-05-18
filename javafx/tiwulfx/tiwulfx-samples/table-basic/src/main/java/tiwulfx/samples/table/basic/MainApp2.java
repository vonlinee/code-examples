package tiwulfx.samples.table.basic;

import com.jfoenix.controls.JFXComboBox;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        List<String> list = List.of("user_info", "class", "grade", "master", "maslsgds");

        SearchableComboBox<String> comboBox = new SearchableComboBox<>();
        comboBox.getItems().addAll(list);
        comboBox.setEditable(true);
        comboBox.getItems().addAll();

        final VBox vBox = new VBox();

        JFXComboBox<String> comboBox1 = new JFXComboBox<>();
        comboBox1.setEditable(true);
        comboBox1.getItems().addAll(list);

        vBox.getChildren().addAll(comboBox, comboBox1);

        Scene scene = new Scene(vBox);
        TiwulFXUtil.setTiwulFXStyleSheet(scene);
        stage.setTitle("Basic TableControl");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 查询下拉框
     * @param comboBox 组件
     * @param list     数据
     * @param showName 要显示的名字
     */
    public static void getComboBox(ComboBox comboBox, ObservableList<Map> list, String showName) {
        comboBox.getItems().addAll(list);
        comboBox.converterProperty().set(new StringConverter<Map>() {
            Map temp = new HashMap();

            @Override
            public String toString(Map object) {
                temp = object;
                return String.valueOf(object.get(showName));
            }

            @Override
            public Map fromString(String string) {
                return temp;
            }
        });
        new AutoCompleteComboBoxListener<>(comboBox);
    }
}
