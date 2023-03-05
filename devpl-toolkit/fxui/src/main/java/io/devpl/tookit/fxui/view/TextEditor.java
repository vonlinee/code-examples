package io.devpl.tookit.fxui.view;

import javafx.beans.NamedArg;
import org.fxmisc.richtext.CaretSelectionBind;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.EditableStyledDocument;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认带行号，滚动条
 */
public class TextEditor extends StyleClassedTextArea {

    {
        getStyleClass().add("code-area");
        // don't apply preceding style to typed text
        setUseInitialStyleForInsertion(true);
        // 行号
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
    }

    /**
     * Creates an area that can render and edit the same {@link EditableStyledDocument} as another {@link CodeArea}.
     */
    public TextEditor(@NamedArg("document") EditableStyledDocument<Collection<String>, String, Collection<String>> document) {
        super(document, false);
    }

    /**
     * Creates an area with no text.
     */
    public TextEditor() {
        super(false);
    }

    /**
     * Creates a text area with initial text content.
     * Initial caret position is set at the beginning of text content.
     *
     * @param text Initial text content.
     */
    public TextEditor(@NamedArg("text") String text) {
        this();
        appendText(text);
        getUndoManager().forgetHistory();
        getUndoManager().mark();
        // position the caret at the beginning
        selectRange(0, 0);
    }

    protected Pattern WORD_PATTERN = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS);
    protected Pattern WORD_OR_SYMBOL = Pattern.compile("([\\W&&[^\\h]]{2}"    // Any two non-word characters (excluding white spaces), matches like:
                    // !=  <=  >=  ==  +=  -=  *=  --  ++  ()  []  <>  &&  ||  //  /*  */
                    + "|\\w*)"              // Zero or more word characters [a-zA-Z_0-9]
                    + "\\h*"                // Both cases above include any trailing white space
            , Pattern.UNICODE_CHARACTER_CLASS);

    /**
     * Skips ONLY 1 number of word boundaries backwards.
     *
     * @param n is ignored !
     */
    @Override
    public void wordBreaksBackwards(int n, SelectionPolicy selectionPolicy) {
        if (getLength() == 0) return;

        CaretSelectionBind<?, ?, ?> csb = getCaretSelectionBind();
        int paragraph = csb.getParagraphIndex();
        int position = csb.getColumnPosition();
        int prevWord = 0;

        if (position == 0) {
            prevWord = getParagraph(--paragraph).length();
            moveTo(paragraph, prevWord, selectionPolicy);
            return;
        }

        Matcher m = WORD_OR_SYMBOL.matcher(getText(paragraph));

        while (m.find()) {
            if (m.start() == position) {
                moveTo(paragraph, prevWord, selectionPolicy);
                break;
            }
            if ((prevWord = m.end()) >= position) {
                moveTo(paragraph, m.start(), selectionPolicy);
                break;
            }
        }
    }

    /**
     * Skips ONLY 1 number of word boundaries forward.
     *
     * @param n is ignored !
     */
    @Override
    public void wordBreaksForwards(int n, SelectionPolicy selectionPolicy) {
        if (getLength() == 0) return;
        CaretSelectionBind<?, ?, ?> csb = getCaretSelectionBind();
        int paragraph = csb.getParagraphIndex();
        int position = csb.getColumnPosition();
        Matcher m = WORD_OR_SYMBOL.matcher(getText(paragraph));
        while (m.find()) {
            if (m.start() > position) {
                moveTo(paragraph, m.start(), selectionPolicy);
                break;
            }
            if (m.hitEnd()) {
                moveTo(paragraph + 1, 0, selectionPolicy);
            }
        }
    }

    @Override
    public void selectWord() {
        if (getLength() == 0) return;
        CaretSelectionBind<?, ?, ?> csb = getCaretSelectionBind();
        int paragraph = csb.getParagraphIndex();
        int position = csb.getColumnPosition();
        Matcher m = WORD_PATTERN.matcher(getText(paragraph));
        while (m.find()) {
            if (m.end() > position) {
                csb.selectRange(paragraph, m.start(), paragraph, m.end());
                return;
            }
        }
    }
}
