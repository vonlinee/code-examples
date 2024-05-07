package org.example.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ProducerClient extends Application {

    ConnectionFactory factory;
    TextField hostField;
    TextField portField;
    TextField queueNameField;
    TextArea contentArea;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Producer");
        BorderPane root = new BorderPane();
        // 顶部
        hostField = new TextField("localhost");
        portField = new TextField("5672");
        queueNameField = new TextField("hello");
        HBox hBox = new HBox(hostField, portField, queueNameField);
        hBox.setSpacing(5);
        root.setTop(hBox);

        contentArea = new TextArea();
        root.setCenter(contentArea);

        HBox bottomBox = new HBox();
        Button sendMsgBtn = new Button("Send");

        sendMsgBtn.setOnAction(event -> this.send());

        bottomBox.getChildren().addAll(sendMsgBtn);
        bottomBox.setAlignment(Pos.CENTER);
        root.setBottom(bottomBox);
        primaryStage.setScene(new Scene(root, 600, 500));

        primaryStage.show();

        Platform.runLater(contentArea::requestFocus);
    }

    public void send() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostField.getText()); // 设置RabbitMQ服务器的地址
        // 在RabbitMQ中，端口设置通常在连接工厂（ConnectionFactory）中指定。
        // 默认情况下，RabbitMQ使用AMQP协议的标准端口5672
        factory.setPort(Integer.parseInt(portField.getText()));
        String queueName = queueNameField.getText();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            String message = contentArea.getText();
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        }
    }
}
