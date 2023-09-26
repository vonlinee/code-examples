package io.devpl.fxui.editor;

/**
 * codemirror支持的主题
 */
public enum Theme {

    XQ_LIGHT("xq-light");

    private final String themName;

    Theme(String themName) {
        this.themName = themName;
    }

    public String getThemName() {
        return themName;
    }
}
