package com.panemu.tiwulfx.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.lang.ref.WeakReference;

public class ClipboardUtils {

    private static WeakReference<Clipboard> clipboardSRef;

    public static void putString(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     * 获取系统剪贴板
     * @return Clipboard对象
     */
    public static Clipboard getSystemClipboard() {
        if (clipboardSRef == null) {
            clipboardSRef = new WeakReference<>(Clipboard.getSystemClipboard());
        } else if (clipboardSRef.get() == null) {
            clipboardSRef = new WeakReference<>(Clipboard.getSystemClipboard());
        }
        return clipboardSRef.get();
    }

    public static boolean hasString() {
        return getSystemClipboard().hasString();
    }
}
