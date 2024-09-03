package org.example.rabbitmq.rabbitmq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Producer {

    // 队列名称，一个RabbitMQ服务器节点有多个队列
    private final static String QUEUE_NAME = "hello";
    // RabbitMQ服务器节点地址
    private final static String HOST = "localhost";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST); // 设置RabbitMQ服务器的地址
        // 在RabbitMQ中，端口设置通常在连接工厂（ConnectionFactory）中指定。
        // 默认情况下，RabbitMQ使用AMQP协议的标准端口5672
        // factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}