package org.example.rabbitmq.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitMQProducerMain {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQProducerMain.class);
    }
}