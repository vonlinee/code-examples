package code.fxutils.gui;

import code.fxutils.support.sample.FileChooserSample;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.fxutils.support.extension.MainFrame;

public class Launcher {

    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        Application.launch(FileChooserSample.class, args);
    }
}
