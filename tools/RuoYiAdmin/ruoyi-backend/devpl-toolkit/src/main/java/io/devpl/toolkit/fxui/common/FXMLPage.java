package io.devpl.toolkit.fxui.common;

import java.net.URL;

public enum FXMLPage {

    NEW_CONNECTION("static/fxml/newConnection.fxml"),
    SELECT_TABLE_COLUMN("static/fxml/table_customization.fxml"),
    TABLE_COLUMN_CONFIG("static/fxml/tableColumnConfigs.fxml"),
    GENERATOR_CONFIG("static/fxml/generatorConfigs.fxml"),
    DICT_CONFIG("static/fxml/dictConfig.fxml");

    private final String fxml;

    FXMLPage(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return this.fxml;
    }

    public URL getLocation() {
        return Thread.currentThread().getContextClassLoader().getResource(fxml);
    }
}
