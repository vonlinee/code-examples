package org.example.rabbitmq.rabbitmq.rabbitmq.exchange;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FanoutExchangeExample {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ服务器地址
        // 用户名和密码，如果RabbitMQ服务器设置了访问控制，需要填写正确的用户名和密码
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明交换机
        String exchangeName = "my_exchange"; // 交换机的名称
        String exchangeType = "fanout"; // 交换机的类型，这里使用fanout类型
        boolean durable = true; // 是否持久化，如果为true，则交换机将在服务器重启后存在
        boolean autoDelete = false; // 是否自动删除，如果为true，则当最后一个队列解绑后交换机将被删除
        AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(exchangeName, exchangeType, durable, autoDelete, null);

        // 创建队列
        channel.queueDeclare("queue1", true, true, false, null);
        channel.queueDeclare("queue2", true, true, false, null);

        // 绑定交换机和队列
        final String bindingKey = UUID.randomUUID().toString();
        channel.queueBind(exchangeName, "queue1", bindingKey);
        channel.queueBind(exchangeName, "queue2", bindingKey);



        TimeUnit.MINUTES.sleep(30);
    }
}
