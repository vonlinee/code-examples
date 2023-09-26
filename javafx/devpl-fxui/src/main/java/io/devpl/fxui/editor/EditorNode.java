package io.devpl.fxui.editor;

import io.devpl.fxui.editor.web.CodeMirrorEditor;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;

public final class EditorNode extends Region {

    private final CodeEditor editor;

    public EditorNode(LanguageMode languageMode) {
        this.editor = CodeMirrorEditor.newInstance(languageMode);
        getChildren().add(this.editor.getView());

    }

    @Override
    protected void layoutChildren() {
        layoutInArea(editor.getView(), 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
    }
}
