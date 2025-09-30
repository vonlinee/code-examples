package com.panemu.tiwulfx.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * 使用代码描述界面的工具类
 * 减少代码
 */
public class SceneGraph {

    /**
     * 生成一个背景
     * @param color              颜色
     * @param radius             半径
     * @param topRightBottomLeft 间隔
     * @return 背景
     */
    public static Background background(Paint color, double radius, double topRightBottomLeft) {
        return new Background(new BackgroundFill(color, new CornerRadii(radius), new Insets(topRightBottomLeft)));
    }

    /**
     * 生成一个按键
     * @param text               文本
     * @param graphic            图形
     * @param actionEventHandler ActionEvent事件处理
     * @return 按键
     */
    public static Button button(String text, Node graphic, EventHandler<ActionEvent> actionEventHandler) {
        Button btn = new Button(text, graphic);
        btn.setOnAction(actionEventHandler);
        return btn;
    }

    /**
     * 生成一个按键
     * @param text               文本
     * @param actionEventHandler ActionEvent事件处理
     * @return 按键
     */
    public static Button button(String text, EventHandler<ActionEvent> actionEventHandler) {
        Button btn = new Button(text);
        btn.setOnAction(actionEventHandler);
        return btn;
    }

    /**
     * 生成边框
     * @param paint  边框颜色
     * @param radius 半径
     * @param width  宽度
     * @return {@link Border}
     */
    public static Border border(Paint paint, double radius, double width) {
        return new Border(new BorderStroke(paint, BorderStrokeStyle.NONE, new CornerRadii(radius), new BorderWidths(width)));
    }
}
