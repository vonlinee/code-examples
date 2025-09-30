package scenegraph.node.parent.control.button;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;

public class JavaFxNote extends Application {
	public static void main(String[] args) {
//        调用launch方法启动应用
		launch(args);
	}

	@Override

	public void start(Stage primaryStage) throws Exception {

		Button btn1 = new Button("btn1");
		btn1.setLayoutX(50);
		btn1.setLayoutY(50);
		btn1.setPrefWidth(50);
		btn1.setPrefHeight(50);

		Group group = new Group();
//        容器添加子组件
		group.getChildren().addAll(btn1);

//        装饰btn1按钮
		btn1.setStyle("-fx-background-color:#00BFFF;" + // 设置背景颜色
				"-fx-background-radius:20;" + // 设置背景圆角
				"-fx-text-fill:#FF0000;" + // 设置字体颜色
				"-fx-border-radius:20;" + // 设置边框圆角
				"-fx-border-color:#FFFF00;" + // 设置边框颜色
				"-fx-border-style:dashed;" + // 设置边框样式
				"-fx-border-width:5;" + // 设置边框宽度
				"-fx-border-insets:-5" // 设置边框插入值
		);

//        指定一个布局类或者根结点
		Scene scene = new Scene(group, 500, 500);

//      第一种快捷键设置方式，也是常用的
		KeyCombination kc1 = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN);
		Mnemonic mnemonic = new Mnemonic(btn1, kc1);
		scene.addMnemonic(mnemonic);

//      第二种快捷键设置方式，不太常用
		KeyCombination kc2 = new KeyCharacterCombination("A", KeyCombination.ALT_DOWN);
		Mnemonic mnemonic2 = new Mnemonic(btn1, kc2);
		scene.addMnemonic(mnemonic2);

//      第三种设置快捷键方式，基本不用
		KeyCombination kc3 = new KeyCodeCombination(KeyCode.K, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN,
				KeyCombination.ALT_DOWN, KeyCombination.META_DOWN, KeyCombination.SHORTCUT_DOWN);
		Mnemonic mnemonic3 = new Mnemonic(btn1, kc3);
		scene.addMnemonic(mnemonic3);

//      第四种设置快捷键方式，最常用，
//      SHORTCUT_DOWN等同于Windows系统的Ctrl,等同于Mac系统的Meta
		KeyCombination kc4 = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
		scene.getAccelerators().put(kc4, new Runnable() {
			@Override
			public void run() {
				System.out.println("run方法");
				play();
			}
		});

		primaryStage.setScene(scene);
		primaryStage.setTitle("骏杰小程序");
		primaryStage.setHeight(768);
		primaryStage.setWidth(1366);
		primaryStage.show();

	}

	public static void play() {
		System.out.println("触发快捷键");
	}

}