package org.example.rabbitmq.param;

import lombok.Data;

import java.util.Map;

@Data
public class MQParam {

    private Boolean consumer = false;

    private String exchange;

    private String routingKey;

    private String queue;

    private Map<String, Object> data;
}
