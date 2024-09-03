package org.example.rabbitmq.rabbitmq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer {

    private final static String QUEUE_NAME = "my_queue"; // 你的队列名称  

    public static void main(String[] argv) throws IOException, TimeoutException {
        // 创建连接工厂  
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ服务器地址  
        factory.setPort(5672); // RabbitMQ服务器端口  
        // 用户名和密码，如果RabbitMQ服务器设置了访问控制，需要填写正确的用户名和密码  
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 创建连接  
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 声明队列，如果不存在则创建  
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 定义消费者  
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("Received '" + message + "'");
                    // 处理消息的逻辑  
                }
            };

            // 开始消费，并传入消费者  
            channel.basicConsume(QUEUE_NAME, true, consumer);
        }
    }
}