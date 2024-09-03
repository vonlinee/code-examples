package org.example.rabbitmq.rabbitmq;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate template = applicationContext.getBean(RabbitTemplate.class);
        template.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                String msg = String.format("消息发送失败，应答码%s, 原因%s, 交换机%s, 路由键%s, 消息%s",
                        returnedMessage.getReplyCode(),
                        returnedMessage,
                        returnedMessage.getReplyText(),
                        returnedMessage.getRoutingKey(),
                        returnedMessage.getMessage());
                System.out.println(msg);
            }
        });
    }
}
