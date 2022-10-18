package io.devpl.codegen.fxui.utils;

@SuppressWarnings("unchecked")
public final class FxmlLoadResult {

    private Object root;
    private Object controller;
    private String location;

    public FxmlLoadResult(Object root, Object controller, String location) {
        this.root = root;
        this.controller = controller;
        this.location = location;
    }

    /**
     * 不能通过new Scene(root)进行构造场景图，因为root已经属于另一个场景图了
     * @param <T>
     * @return
     */
    public <T> T getRoot() {
        return (T) root;
    }

    public void setRoot(Object root) {
        this.root = root;
    }

    public <T> T getController() {
        return (T) controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FxmlLoadResult) {
            return this.location.equals(((FxmlLoadResult) obj).location);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}