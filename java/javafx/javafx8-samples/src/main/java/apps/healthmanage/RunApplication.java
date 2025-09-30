package apps.healthmanage;

import java.util.ArrayList;
import java.util.List;

import apps.healthmanage.utils.DataUtil;
import apps.healthmanage.utils.ProjectValues;
import apps.healthmanage.utils.StyleUtil;
import apps.healthmanage.views.PageFactory;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * PTU启动类
 * 
 * @author huhailong
 *
 */
public class RunApplication extends Application {
	
	private HBox root;
	private Node currentPageNode = null;
	private VBox leftMenu;
	private Integer currentMenuIndex;
	private Integer tempIndex;

	@Override
	public void start(Stage primaryStage) throws Exception {
		double screenWidth = Screen.getPrimary().getBounds().getWidth();
		double screenHeight = Screen.getPrimary().getBounds().getHeight();
		root = new HBox();
		leftMenu = (VBox) getLeftMenu(root);
		root.getChildren().add(leftMenu);
		currentPageNode = PageFactory.createPageService("个人中心").generatePage(root);
		HBox.setHgrow(currentPageNode, Priority.ALWAYS);
		root.getChildren().add(currentPageNode);
		StyleUtil.setPaneBackground(root, Color.WHITE);
		Scene scene = new Scene(root, screenWidth * 0.8, screenHeight * 0.8);
		primaryStage.setTitle("健康管理");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 获取左侧菜单栏
	 */
	private Node getLeftMenu(Pane root) {
		double leftWidth = ProjectValues.leftMenuWidth;
		VBox vbox = new VBox();
		vbox.setMinHeight(root.getPrefHeight());
		vbox.setMinWidth(leftWidth);
		StyleUtil.setPaneBackground(vbox, Color.web(ProjectValues.COLOR_PRIMARY));
		// 增加菜单中的项目
		vbox.getChildren().addAll(getLeftMenuItemList(leftWidth));
		return vbox;
	}
	
	/**
	 * 右侧页面路由
	 */
	private Node routePage(Pane root, String itemName) {
		return PageFactory.createPageService(itemName).generatePage(root);
	}

	/**
	 * 生成左侧菜单按钮
	 */
	private List<Button> getLeftMenuItemList(double width) {
		double buttonHeight = 30;
		List<Button> buttonList = new ArrayList<>(3);
		String[] itemNames = { "个人中心", "健康统计", "增加记录" };
		for (String name : itemNames) {
			Button button = new Button(name);
			button.setMinWidth(width);
			button.setMinHeight(buttonHeight);
			StyleUtil.setButtonBackground(button, Color.web(ProjectValues.COLOR_PRIMARY), Color.WHITE);
			//增加鼠标移动到菜单上到hover效果
			button.setOnMouseMoved(event->{
				if(currentMenuIndex==null||!button.getText().equals(itemNames[currentMenuIndex])) {
					StyleUtil.setButtonBackground(button, Color.web(ProjectValues.COLOR_HOVER), Color.WHITE);StyleUtil.setFont(button, Color.WHITE, -1);
				}else {
					StyleUtil.setButtonBackground(button, Color.web(ProjectValues.COLOR_HOVER), Color.web(ProjectValues.COLOR_SELECTED));
				}
			});		
			button.setOnMouseExited(event->{
				if(currentMenuIndex==null||!button.getText().equals(itemNames[currentMenuIndex])) {					
					StyleUtil.setButtonBackground(button, Color.web(ProjectValues.COLOR_PRIMARY), Color.WHITE);
				}else {
					StyleUtil.setButtonBackground(button, Color.web(ProjectValues.COLOR_PRIMARY), Color.web(ProjectValues.COLOR_SELECTED));
				}
			});
			button.setOnMouseClicked(event->{
				currentMenuIndex = DataUtil.getIndexForArray(itemNames,button.getText());
				currentPageNode = routePage(root,name);
				root.getChildren().remove(1);	//清楚右侧页面路由组件节点
				HBox.setHgrow(currentPageNode, Priority.ALWAYS);
				root.getChildren().add(currentPageNode);
				StyleUtil.setFont(button, Color.web(ProjectValues.COLOR_SELECTED), -1);
				//选中状态逻辑
				if(tempIndex!=null) {					
					Button node = (Button) leftMenu.getChildren().get(tempIndex);
					StyleUtil.setFont(node, Color.WHITE, -1);	//清空选中状态样式
					StyleUtil.setButtonBackground(node, Color.web(ProjectValues.COLOR_PRIMARY), Color.WHITE);
				}
				StyleUtil.setFont(button, Color.web(ProjectValues.COLOR_SELECTED), -1);	//设置选中样式
				tempIndex = currentMenuIndex;
				
			});
			buttonList.add(button);
		}
		return buttonList;
	}
	
	public static void main(String[] args) {
		launch();
	}

}
