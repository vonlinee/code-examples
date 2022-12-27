package io.devpl.toolkit.fxui.framework.mvc.dialog;

import java.util.HashMap;
import java.util.Map;

import io.devpl.toolkit.fxui.framework.utils.I18N;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.stage.Window;

/**
 * A modal dialog which displays a piece of text and provides a Close button
 * and a Copy button.
 * 
 * 
 */
public class TextViewDialog extends AbstractModalDialog {
    
    @FXML
    private TextArea textArea;
    
    /*
     * Public
     */
    
    
    public TextViewDialog(Window owner) {
        super(TextViewDialog.class.getResource("TextViewDialog.fxml"), null, owner); //NOI18N
        setOKButtonVisible(false);
        setActionButtonVisible(true);
        setCancelButtonTitle(I18N.getString("label.close"));
        setActionButtonTitle(I18N.getString("label.copy"));
    }
    
    public void setText(String text) {
        textArea.setText(text);
    }
    
    public String getText() {
        return textArea.getText();
    }
    
    /*
     * AbstractModalDialog
     */
    
    @Override
    protected void controllerDidLoadContentFxml() {
        assert textArea != null;
    }
    
    @Override
    protected void okButtonPressed(ActionEvent e) {
        // Should not be called because ok button is hidden
        throw new IllegalStateException();
    }
    
    @Override
    protected void cancelButtonPressed(ActionEvent e) {
        getStage().close();
    }
    
    @Override
    protected void actionButtonPressed(ActionEvent e) {
        final Map<DataFormat, Object> content = new HashMap<>();
        content.put(DataFormat.PLAIN_TEXT, getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
    
}
