package de.saxsys.mvvmfx.samples.view;

import de.saxsys.mvvmfx.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.io.IOException;

@FxmlLocation(location = "static/fxml/test.fxml")
@ViewController(viewModel = MainViewModel.class)
public class MainView extends AbstractFxmlView<AnchorPane> {

    @FXML
    public Button testBtn;
    @FXML
    public AnchorPane rootAnp;
    @Inject
    public MainViewModel viewModel;

    @FXML
    public void showRoot(ActionEvent event) throws IOException {
        final ViewComponent viewElement1 = ViewLoader.loadByClass(MainView.class);
        final ViewComponent viewElement2 = ViewLoader.loadByClass(MainView.class);
    }
}
