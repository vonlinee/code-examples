package com.panemu.tiwulfx.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ClipboardUtils {

    public static void putString(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    public static boolean hasString() {
        return Clipboard.getSystemClipboard().hasString();
    }
}
