package org.example.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {

    public static ConnectionFactory newConnectionFactory() {
        return newConnectionFactory("127.0.0.1");
    }

    public static ConnectionFactory newConnectionFactory(String ip) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(AMQP.PROTOCOL.PORT); // RabbitMQ服务器端口
        connectionFactory.setHost(ip); // RabbitMQ服务器地址
        // 用户名和密码，如果RabbitMQ服务器设置了访问控制，需要填写正确的用户名和密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }
}
