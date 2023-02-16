package io.devpl.tookit.utils.fx;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * javafx组件拖动工具类
 * @author 徐志林
 * @createTime 2020-04-23 10:46
 */
public class NodeDragUtil {
    public static void addNodeDragListener(Node node){
        new DragListener(node).enableDrag(node);
    }

    public static void addAllDragListener(List<Node> nodeList){
        nodeList.forEach(node -> {
            new DragListener(node).enableDrag(node);
        });
    }

    static class DragListener implements EventHandler<MouseEvent> {
        private double xOffset = 0;
        private double yOffset = 0;
        private Node node;

        public DragListener(Node node) {
            this.node = node;
        }

        @Override
        public void handle(MouseEvent event) {
            event.consume();
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                xOffset = event.getX();
                yOffset = event.getY();
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                node.setTranslateX((node.getTranslateX()+event.getX() - xOffset));
                node.setTranslateY((node.getTranslateY()+event.getY() - yOffset));
            }
        }

        public void enableDrag(Node node) {
            node.setOnMousePressed(this);
            node.setOnMouseDragged(this);
        }
    }
}

