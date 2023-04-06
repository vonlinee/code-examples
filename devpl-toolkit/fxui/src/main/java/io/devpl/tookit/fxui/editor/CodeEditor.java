package io.devpl.tookit.fxui.editor;

import javafx.scene.Parent;

import java.util.List;
import java.util.function.Function;

public interface CodeEditor {
    String getContent();

    void setContent(String newContent, boolean markClean);

    boolean isClean();

    void markClean();

    Position getCursorPosition();

    void setCursorPosition(Position position);

    boolean isEditorInitialized();

    /**
     * 初始化回调
     *
     * @param runAfterLoading WebView加载完成之后调用
     */
    void init(Runnable... runAfterLoading);

    /**
     * 编辑器对应的节点 Node
     *
     * @return 编辑器对应的节点 Node
     */
    Parent getView();

    /**
     * 编辑器是否只读
     *
     * @return 编辑器是否只读
     */
    boolean isReadOnly();

    void setReadOnly(boolean readOnly);

    String getMode();

    void setMode(String mode);

    void setMode(LanguageMode mode);

    //TODO add includeJSModules back in later
    //void includeJSModules(String[] modules, Runnable runnable);

    String getTheme();

    void setTheme(String theme, String... cssFile);

    /**
     * 回调
     *
     * @param runnable
     */
    void runWhenReady(Runnable runnable);

    void setAutoCompleteFunction(Function<String, List<String>> autoCompleteFunction);

    Function<String, List<String>> getAutoCompleteFunction();
}