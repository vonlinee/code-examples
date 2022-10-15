package io.devpl.codegen.fxui.utils;

public enum FXMLPage {

    NEW_CONNECTION("fxml/connection_config.fxml"),
    SELECT_TABLE_COLUMN("fxml/customize_column.fxml"),
    TABLE_COLUMN_CONFIG("fxml/column_config.fxml"),
    GENERATOR_CONFIG("fxml/codegen_config.fxml"),
    ;

    private final String fxml;

    FXMLPage(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return this.fxml;
    }
}
