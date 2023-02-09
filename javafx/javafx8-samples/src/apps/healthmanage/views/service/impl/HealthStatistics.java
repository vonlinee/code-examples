package apps.healthmanage.views.service.impl;

import apps.healthmanage.utils.StyleUtil;
import apps.healthmanage.views.service.IPageService;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * 健康统计
 * @author huhailong
 *
 */
public class HealthStatistics implements IPageService {

	@Override
	public Node generatePage(Pane root) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		Label test = new Label("健康统计页面");
		StyleUtil.setFont(test, Color.BLACK, 20);
		vbox.getChildren().add(test);
		return vbox;
	}

}
