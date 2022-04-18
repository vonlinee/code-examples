package sample.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class SyncProducer {
	public static void main(String[] args) throws Exception {
		// Instantiate with a producer group name.
		DefaultMQProducer producer = new DefaultMQProducer("producer-group-1");
		// Specify name server addresses.
		producer.setNamesrvAddr("localhost:9876");
		// Launch the instance.
		producer.start();
		// 发送100条消息
		for (int i = 0; i < 100; i++) {
			// Create a message instance, specifying topic, tag and message body.
			Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
					("Hello RocketMQ " + i).getBytes("UTF-8") /* Message body */
			);

			// Call send message to deliver message to one of brokers.
			SendResult sendResult = producer.send(msg);
			System.out.printf("%s%n", sendResult);
		}
		// Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}
}
