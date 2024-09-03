package org.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class KafkaProducerTransactionExample {
    public static void main(String[] args) {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.ACKS_CONFIG, "1");
        configMap.put(ProducerConfig.RETRIES_CONFIG, 5);
        configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        configMap.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000); // 3s
        configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // 开启生产者幂等性


        // 创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(configMap);

        // 创建消息数据
        ProducerRecord<String, String> record = new ProducerRecord<>("topicName", "key", "value");

        // 初始化事务
        producer.initTransactions();

        producer.beginTransaction();
        // 在事务内提交已经消费的偏移量
        producer.sendOffsetsToTransaction(null, "");

    }
}
