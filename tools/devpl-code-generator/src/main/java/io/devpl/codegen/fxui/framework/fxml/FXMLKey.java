package io.devpl.codegen.fxui.framework.fxml;

public final class FXMLKey {

    private final String name;

    public FXMLKey(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FXMLKey) {
            return name.equals(((FXMLKey) obj).name);
        }
        return false;
    }
}
