package io.devpl.toolkit.framework.fxml;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadListener;

/**
 * FXML加载监听器
 */
public class FXMLLoadListener implements LoadListener {

    private final FXMLLoader loader;

    public FXMLLoadListener(FXMLLoader loader) {
        this.loader = loader;
    }

    /**
     * FXML中导入类型
     * @param target the target of the import
     */
    @Override
    public void readImportProcessingInstruction(String target) {

    }

    @Override
    public void readLanguageProcessingInstruction(String language) {
        System.out.println(language);
    }

    @Override
    public void readComment(String comment) {
        System.out.println(comment);
    }

    @Override
    public void beginInstanceDeclarationElement(Class<?> type) {

    }

    @Override
    public void beginUnknownTypeElement(String name) {

    }

    @Override
    public void beginIncludeElement() {

    }

    @Override
    public void beginReferenceElement() {
    }

    @Override
    public void beginCopyElement() {

    }

    @Override
    public void beginRootElement() {

    }

    @Override
    public void beginPropertyElement(String name, Class<?> sourceType) {

    }

    @Override
    public void beginUnknownStaticPropertyElement(String name) {

    }

    @Override
    public void beginScriptElement() {

    }

    @Override
    public void beginDefineElement() {

    }

    @Override
    public void readInternalAttribute(String name, String value) {

    }

    @Override
    public void readPropertyAttribute(String name, Class<?> sourceType, String value) {

    }

    @Override
    public void readUnknownStaticPropertyAttribute(String name, String value) {

    }

    @Override
    public void readEventHandlerAttribute(String name, String value) {

    }

    @Override
    public void endElement(Object value) {

    }
}
