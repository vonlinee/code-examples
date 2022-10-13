package io.devpl.codegen.fxui.framework;

public class Intent extends FXEvent {

    private String action;
    private Bundle bundle;

    public <T> T get(String name) {
        return bundle.get(name);
    }
}
