package org.example.rabbitmq.rabbitmq.rabbitmq.controller;

import org.example.rabbitmq.rabbitmq.rabbitmq.config.RabbitmqConfig;
import org.example.rabbitmq.rabbitmq.rabbitmq.param.MQParam;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/rabbitmq")
public class RabbitMQController {

    @Resource
    RabbitTemplate template;

    /**
     * 生产者
     *
     * @param param
     */
    @PostMapping(value = "/producer")
    public void producer(@RequestBody MQParam param) {
        template.convertAndSend(param.getRoutingKey(), param.getData());
    }

    /**
     * 消费者
     *
     * @param param
     */
    @PostMapping(value = "/consumer")
    public void consumer(@RequestBody MQParam param) {
        String exchange = param.getExchange();
        String routingKey = param.getRoutingKey();
        template.convertAndSend(routingKey, param.getData());
    }

    @GetMapping("/sendToClient")
    public String sendToClient() {
        String message = "server message sendToClient";
        for (int i = 0; i < 10000; i++) {
            template.convertAndSend(RabbitmqConfig.QUEUE_INFORM_EMAIL, message + " : " + i);
        }
        return message;
    }

    @Component
    static class ConsumerListener {

        /**
         * 监听指定的队列
         *
         * @param message 收到的消息
         */
        @RabbitListener(queues = RabbitmqConfig.QUEUE_INFORM_EMAIL)
        public void consumer1(String message) {
            System.out.println("接收者 Received message: " + message);
        }


        @RabbitListener(queuesToDeclare = @Queue(
                name = "lazy.queue",
                durable = "true",
                arguments = @Argument(name = "x-queue-mode", value = "lazy")
        ))
        public void consumer2(String message) {

            throw new MessageConversionException("消息转换失败");
        }
    }
}
