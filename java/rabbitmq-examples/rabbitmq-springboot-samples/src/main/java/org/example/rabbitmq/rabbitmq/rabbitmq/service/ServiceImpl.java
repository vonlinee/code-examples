package org.example.rabbitmq.rabbitmq.rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ServiceImpl {

    @Resource
    RabbitTemplate template;
}