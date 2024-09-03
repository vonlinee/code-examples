package org.example.rabbitmq.rabbitmq.rabbitmq;

import com.rabbitmq.client.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ConsumerPullClient extends Application {

    ConnectionFactory factory;
    TextField hostField;
    TextField portField;
    TextField queueNameField;
    TextArea contentArea;
    Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Consumer");

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
        Button sendMsgBtn = new Button("Pull");

        sendMsgBtn.setOnAction(event -> this.pull());

        bottomBox.getChildren().addAll(sendMsgBtn);
        bottomBox.setAlignment(Pos.CENTER);
        root.setBottom(bottomBox);
        primaryStage.setScene(new Scene(root, 600, 500));

        primaryStage.show();

        Platform.runLater(contentArea::requestFocus);
    }

    public void pull() {
        if (factory != null) {
            return;
        }

        // 创建连接工厂
        factory = new ConnectionFactory();
        factory.setHost(hostField.getText()); // RabbitMQ服务器地址
        factory.setPort(Integer.parseInt(portField.getText())); // RabbitMQ服务器端口
        // 用户名和密码，如果RabbitMQ服务器设置了访问控制，需要填写正确的用户名和密码
        factory.setUsername("guest");
        factory.setPassword("guest");

        String queueName = queueNameField.getText();

        // 创建连接
        try {
            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();
            // 声明队列，如果不存在则创建
            channel.queueDeclare(queueName, false, false, false, null);

            // 定义消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    contentArea.appendText("Received => '" + message + "'\n");
                }
            };

            // 开始消费，并传入消费者
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
