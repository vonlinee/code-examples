package scenegraph.node.parent.pane.tabpane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane ap = new AnchorPane();
		TabPane tabPane = new TabPane();
		tabPane.setPrefHeight(300);
		tabPane.setPrefWidth(300);
		// tabPane.setStyle("-fx-background-color: aqua");

		Tab tab1 = new Tab("TAB1");// 相当于一个面板，可以关闭的那种
		Tab tab2 = new Tab("TAB2");
		Tab tab3 = new Tab("TAB3");// 完全关闭之后就剩一个tabpane
		
		
		

		HBox hbox = new HBox();
		// hbox.setStyle("-fx-background-color: #6b107b");// 单一设置第一个lab的背景颜色
		hbox.setAlignment(Pos.CENTER);// 将按钮剧中
		hbox.getChildren().addAll(new Button("B1"), new Button("B2"));
		tab1.setContent(hbox);

		VBox vbox = new VBox();
		// vbox.setStyle("-fx-background-color: #277b10");// 单一设置第一个lab的背景颜色
		vbox.setAlignment(Pos.CENTER);// 将按钮剧中
		vbox.getChildren().addAll(new Button("B3"), new Button("B4"));

		tab2.setContent(vbox);

        ImageView iv = new ImageView("resources/image/mac.png");
        
        iv.setFitHeight(25);
        iv.setFitWidth(25);
        
        
       // tabPane.setRotateGraphic(false);
        

        tab2.setGraphic(iv);
		tab2.setClosable(false);// 将2的关闭按钮给移除

		tabPane.getSelectionModel().select(tab2);// 默认进去就显示2

		tabPane.getTabs().addAll(tab1, tab2, tab3);// 一个pane上可以放置多个
		tabPane.setSide(Side.RIGHT);// 将选择面板移动到右侧，默认在上，但是图片文字会跟着改动，下方为设置为正向
		
		
		//tabPane.setRotateGraphic(false);

		ap.getChildren().addAll(tabPane);
		AnchorPane.setTopAnchor(tabPane, 200.0);
		AnchorPane.setLeftAnchor(tabPane, 200.0);
		Scene scene = new Scene(ap);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Java FX  ");
		primaryStage.setWidth(800);
		primaryStage.setHeight(800);
		primaryStage.show();
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {// 监听点击的哪个
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				System.out.println(newValue.getText());
			}
		});
		tab1.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Tab t = (Tab) event.getSource();
				System.out.println("这是::" + t.getText() + "改变");// 会显示两个，因为改变了两个tab的状态
			}
		});
		ap.setOnMouseClicked(new EventHandler<MouseEvent>() {// 点击ap新添加一个tab
			@Override
			public void handle(MouseEvent event) {
				tabPane.getTabs().add(new Tab("新加的tab"));
			}
		});
		
		
		
		tabPane.setTabClosingPolicy(null);
		
		
		
		
	}
}
