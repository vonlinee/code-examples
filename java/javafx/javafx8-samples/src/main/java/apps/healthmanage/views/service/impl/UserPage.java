package apps.healthmanage.views.service.impl;

import apps.healthmanage.views.service.IPageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * 用户个人中心页面
 * @author huhailong
 *
 */
public class UserPage implements IPageService {

	@Override
	public Node generatePage(Pane root) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		ImageView userAvatar = getUserAvatar();
		ListView<String> userInfoList = getUserInfoList();
		vbox.getChildren().add(userAvatar);
		vbox.getChildren().add(userInfoList);
		return vbox;
	}
	
	private ImageView getUserAvatar() {
		Image avatar = new Image("https://www.huhailong.vip/img/wx.jpg", 200, 200, true, true);
		ImageView imageView = new ImageView(avatar);
		return imageView;
	}
	
	private ListView<String> getUserInfoList(){
		ObservableList<String> items = FXCollections.observableArrayList("用户名: Huhailong","昵称: 胡海龙","出生年月: 1996年11月21日","年龄: 26岁","星座: 天蝎座","签名: 办法总比困难多！");
		ListView<String> userInfoList = new ListView<>(items);	
		userInfoList.setMaxWidth(400);
		userInfoList.setMaxHeight(150);
		return userInfoList;
	}

	
}
