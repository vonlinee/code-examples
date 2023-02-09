package scenegraph.stage;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

/**
 * javafx组件放大缩小工具
 * @author 徐志林
 * @createTime 2020-04-23 10:33
 */
public class ZoomUtil {
    public static void zoom(Node node) {
        node.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        double zoomNumber = 1.05;
                        double deltaY = event.getDeltaY();

                        if (deltaY < 0) {
                            zoomNumber = 0.95;
                        }
                        node.setScaleX(node.getScaleX() * zoomNumber);
                        node.setScaleY(node.getScaleY() * zoomNumber);
                        event.consume();
                    }
                }
        );
    }
}
