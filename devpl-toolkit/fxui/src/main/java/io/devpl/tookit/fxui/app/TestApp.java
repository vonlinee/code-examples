package io.devpl.tookit.fxui.app;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import io.devpl.tookit.fxui.view.components.OptionTableView;
import io.fxtras.JavaFXApplication;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestApp extends JavaFXApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FormRenderer renderer = new FormRenderer(Form.of(
                Group.of(
                        Field.ofStringType("")
                                .label("作者"),
                        Field.ofStringType("")
                                .label("项目名称")
                                .required("项目名称为空"),
                        Field.ofIntegerType(10)
                                .label("年龄")
                                .required("age is empty"),
                        Field.ofSingleSelectionType(Arrays.asList("Zürich (ZH)", "Bern (BE)"), 1)
                                .label("Capital"),
                        Field.ofMultiSelectionType(Arrays.asList("Africa", "Asia"), Collections.singletonList(2))
                                .label("Continent")
                                .render(new SimpleCheckBoxControl<>()),
                        Field.ofMultiSelectionType(Arrays.asList("Zürich (ZH)", "Bern (BE)"), List.of(1))
                                .label("Biggest Cities")
                )
        ).title("Login"));

        primaryStage.setScene(new Scene(renderer, 600, 400));
        primaryStage.show();
    }
}
