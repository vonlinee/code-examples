package sample.editor.Highlighter;

import org.fxmisc.richtext.StyleClassedTextArea;
import sample.editor.controller.Utils;

public class SyntaxHighlighter {
    private final StyleClassedTextArea codeArea;

    public SyntaxHighlighter(StyleClassedTextArea codeArea) {
        this.codeArea = codeArea;
    }

    public void start(String fileName) {
        String ext = new Utils().getExtension(fileName);
        switch (ext) {
            case "java":
                new JavaKeywordHighlighter().start(codeArea);
                break;
            case "py":
                new PythonKeywordHighlighter().start(codeArea);
                break;
            case "c":
                new CKeywordHighlighter().start(codeArea);
                break;
            case "cpp":
                new CppKeywordHighlighter().start(codeArea);
                break;
            default:
                break;
        }
    }
}
