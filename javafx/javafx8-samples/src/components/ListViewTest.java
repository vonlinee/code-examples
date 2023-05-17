package components;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListViewTest extends Application {
    int index;
    String data;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane an = new AnchorPane();
        String s1 = "A先生-18-男";
        String s2 = "B先生-18-男";
        String s3 = "C先生-18-男";
        String s4 = "D先生-18-男";
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(s1, s2, s3, s4);

        ListView<String> listview = new ListView<>();
        //占位符 当listview没有数据时显示占位符
        listview.setPlaceholder(new Label("没有数据"));
        //添加一个可观测的列表显示
        listview.setItems(list);
        listview.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            int position = 0;
            String positionstr = "";

            @Override
            public ListCell<String> call(ListView<String> param) {
                Label label = new Label();

                ListCell<String> cell = new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            label.setText(item);
                            label.setPrefHeight(20);
                            label.setFont(new Font(15.0));
                            this.setGraphic(label);
                        }
                    }
                };
                cell.hoverProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue && !"".equals(label.getText())) {
                            position = param.getItems().indexOf(label.getText());
                            param.getFocusModel().focus(position);
                        }
                    }
                });
                //设置如何处理拖拽元素，先删除拖拽位置元素，再将其添加在拖拽位置
                cell.setOnDragDropped(new EventHandler<DragEvent>() {

                    @Override
                    public void handle(DragEvent event) {
                        if (position == -1) {
                            position = param.getItems().size() - 1;
                        }
                        String value = event.getDragboard().getString();
                        param.getItems().remove(index);
                        param.getItems().add(position, value);
                        System.out.println(value);
                        param.getSelectionModel().select(position);
                    }
                });

                cell.setOnDragOver(new EventHandler<DragEvent>() {

                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                });
                //实时获取拖拽位置
                cell.setOnDragEntered(new EventHandler<DragEvent>() {

                    @Override
                    public void handle(DragEvent event) {
                        position = param.getItems().indexOf(label.getText());
                        positionstr = label.getText();
                        param.getFocusModel().focus(position);//得到焦点
                    }
                });
                //创建拖拽面板加入选中拖拽元素
                cell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(data);
                        db.setContent(content);
                    }
                });
                return cell;
            }
        });
        //设置单元格尺寸
        an.getChildren().addAll(listview);
        AnchorPane.setTopAnchor(listview, 50.0);
        AnchorPane.setLeftAnchor(listview, 30.0);
        Scene scene = new Scene(an);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javafx");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);
        primaryStage.show();
        listview.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                index = newValue.intValue();
            }
        });
        listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                data = newValue;
            }
        });
    }
}

