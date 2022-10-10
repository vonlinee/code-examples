package io.devpl.codegen.fxui.view;

public enum FXMLPage {

    NEW_CONNECTION("fxml/connection_config.fxml"),
    SELECT_TABLE_COLUMN("fxml/selectTableColumn.fxml"),
    TABLE_COLUMN_CONFIG("fxml/tableColumnConfigs.fxml"),
    GENERATOR_CONFIG("fxml/generatorConfigs.fxml"),
    ;

    private final String fxml;

    FXMLPage(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return this.fxml;
    }
}
