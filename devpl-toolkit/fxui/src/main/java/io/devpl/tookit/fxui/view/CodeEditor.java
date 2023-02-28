package io.devpl.tookit.fxui.view;

import io.devpl.tookit.utils.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Popup;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.LinkedList;

public class CodeEditor {

    public CodeEditor() {
        ListView<String> listView = new ListView<String>();
        // github上开源项目richtextfx的一个组件
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        Popup popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().addAll(listView);

        codeArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // 截取newvalue光标前最后一个非字母到光标位置，作为提示信息输入源
            if (!StringUtils.isEmpty(newValue)) {
                int caretPosition = codeArea.getCaretPosition();
                newValue = newValue.substring(0, caretPosition);
                int index = -1;
                char[] chars = newValue.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    boolean matches = String.valueOf(chars[i]).matches("^[A-Za-z0-9_]+$");
                    if (!matches) {
                        index = i;
                    }
                }
                String substring = newValue.substring(index + 1);

                if (StringUtils.isEmpty(substring)) {
                    popup.hide();
                    return;
                }
                LinkedList<String> linkedList = new LinkedList<>();
                ObservableList<String> filteredList = FXCollections.observableArrayList(linkedList);
                if (filteredList.size() > 0) {
                    listView.setItems(filteredList);
                    popup.show(codeArea.getScene().getWindow());
                } else {
                    popup.hide();
                }
            } else {
                popup.hide();
            }
        });
    }
}
