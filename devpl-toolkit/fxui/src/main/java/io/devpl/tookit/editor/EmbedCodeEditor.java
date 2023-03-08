package io.devpl.tookit.editor;

import org.fxmisc.flowless.VirtualizedScrollPane;

public class EmbedCodeEditor extends VirtualizedScrollPane<CodeEditor> {

    private CodeEditor editor;

    public EmbedCodeEditor() {
        this(new CodeEditor());
        editor = getContent();
    }

    EmbedCodeEditor(CodeEditor content) {
        super(content);
    }

    public String getText() {
        return editor.getText();
    }
}
