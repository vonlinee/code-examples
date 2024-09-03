package org.example.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SimpleKafkaConsumer2 {

    @Test
    public void test1() {
        // Kafka 消费者配置  
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        // 关闭自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // 配置序列化
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // 跟踪偏移量
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        int count = 0;
        // 创建 Kafka 消费者
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            // 订阅一个或多个主题  
            consumer.subscribe(Arrays.asList("mytopic", "mytopic1"));
            // 持续消费消息
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new
                            OffsetAndMetadata(record.offset() + 1, "no metadata"));
                    // 决定每处理 1000 条记录就提交一次偏移量
                    if (count % 1000 == 0)
                        consumer.commitAsync(currentOffsets, null);
                    count++;
                }
            }
        }
    }
}