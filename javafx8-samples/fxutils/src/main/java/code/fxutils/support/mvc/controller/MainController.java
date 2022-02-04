package code.fxutils.support.mvc.controller;

import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("initialize " + location.toExternalForm());
        LOG.info("ResourceBundle " + resources.getBaseBundleName());

    }
}
