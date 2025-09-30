/*
 * MIT License
 *
 * Copyright (c) 2020-2022 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eu.mihosoft.monacofx;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编辑器节点
 */
public class MonacoEditor extends Parent {

    private final WebView view;
    private final WebEngine engine;

    private final Editor editor;
    private final SystemClipboardWrapper systemClipboardWrapper;

    private static String url;

    static final String index = "";

    public String findMonacoEditorHtml() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("eu/mihosoft/monacofx/monaco-editor-0.20.0/index.html");
        if (resource == null) {
            throw new RuntimeException("Monaco Editor初始化失败");
        }
        return resource.toExternalForm();
    }

    public MonacoEditor() {
        view = new WebView();
        view.setStyle("-fx-background-color: green");

        getChildren().add(view);
        if (url == null) {
            url = findMonacoEditorHtml();
        }
        engine = view.getEngine();

        engine.setOnAlert(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("JS Alert");
            alert.setContentText(event.getData());
            alert.showAndWait();
        });

        engine.load(url);

        editor = new Editor(engine);

        systemClipboardWrapper = new SystemClipboardWrapper();
        ClipboardBridge clipboardBridge = new ClipboardBridge(getEditor().getDocument(), systemClipboardWrapper);
        engine.getLoadWorker().stateProperty().addListener((o, old, state) -> {
            // Worker初始化完毕
            if (state == Worker.State.SUCCEEDED) {
                // 注入脚本对象
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("clipboardBridge", clipboardBridge);

                AtomicBoolean jsDone = new AtomicBoolean(false);
                AtomicInteger attempts = new AtomicInteger();

                // 类似于浏览器加载js并执行js的过程，异步加载JS
                Thread thread = new Thread(() -> {
                    while (!jsDone.get()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // check if JS execution is done.
                        Platform.runLater(() -> {
                            Object jsEditorObj = window.call("getEditorView");
                            if (jsEditorObj instanceof JSObject) {
                                editor.setEditor(window, (JSObject) jsEditorObj);
                                jsDone.set(true);
                            }
                        });

                        if (attempts.getAndIncrement() > 10) {
                            throw new RuntimeException("Cannot initialize editor (JS execution not complete). Max number of attempts reached.");
                        }
                    }
                });
                thread.start();
            }
        });

        addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // 执行脚本
            Object obj = engine.executeScript("editorView.getModel().getValueInRange(editorView.getSelection())");
            systemClipboardWrapper.handleCopyCutKeyEvent(event, obj);
        });
    }

    @Override
    protected double computePrefWidth(double height) {
        System.out.println(height);
        return view.prefWidth(height);
    }

    @Override
    protected double computePrefHeight(double width) {
        System.out.println(width);
        return view.prefHeight(width);
    }

    public Editor getEditor() {
        return editor;
    }
}
