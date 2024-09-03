package org.example.rabbitmq.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProviderTest {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Test
    public void test2() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(AMQP.PROTOCOL.PORT); // RabbitMQ服务器端口
        connectionFactory.setHost("127.0.0.1"); // RabbitMQ服务器地址
        // 用户名和密码，如果RabbitMQ服务器设置了访问控制，需要填写正确的用户名和密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Map<String, Object> params = new HashMap<>();
        params.put("x-max-priority", 10);

        try (Connection connection = connectionFactory.newConnection()){
            Channel channel = connection.createChannel();

            channel.queueDeclare("queue-hello", true, false, false, params);

            Queue queue = QueueBuilder.durable()
                    .maxPriority(10)
                    .build();

            Message message = MessageBuilder.withBody("hel".getBytes(StandardCharsets.UTF_8))
                    .setPriority(10)
                    .build();
        }
    }

    @Test
    public void test1() {
        Message msg = MessageBuilder.withBody("Hello".getBytes(StandardCharsets.UTF_8))
                .setExpiration("")
                .build();
    }
}
