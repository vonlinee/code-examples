package org.example.rabbitmq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RequestMapping("rabbitmq/producer")
@RestController
public class PublishController {

    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * 发布消息
     */
    @PostMapping("/publish")
    public void publishMsg() {

    }

    /**
     * 发布消息
     */
    @PostMapping("/publish/confirm")
    public void publishMsgConfirm() {
        // 无参，默认为UUID
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            /**
             * Future发生异常时的处理逻辑，失败和MQ无关，不是ACK/NACK
             * @param ex 发生的异常
             */
            @Override
            public void onFailure(Throwable ex) {
                log.error("发送失败", ex);
            }

            /**
             * 回调成功，不是指消息发送成功，接收到回执后的处理逻辑
             * @param result 回调参数
             */
            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if (result.isAck()) {
                    log.info("消息发送成功, ACK");
                } else {
                    log.error("消息发送失败, NACK 原因:{}", result.getReason());
                }
            }
        });
        rabbitTemplate.convertAndSend("color", "red", "msg", cd);
    }

    @PostMapping("/publish/pageout")
    public void publishMsgPageOut() {
        Message msg = MessageBuilder.withBody("hello".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT).build();
        for (int i = 0; i < 100_0000; i++) {
            rabbitTemplate.convertAndSend("red", msg);
        }
    }

    @Bean
    public Queue lazyQueue() {
        return QueueBuilder.durable("lazy.queue")
                .lazy()
                .build();
    }
}
