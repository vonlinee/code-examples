package code.example.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer {
	
	private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
	private static final String QUEUE_NAME_1 = "queue-01";

	public static final void consumeMessage() throws JMSException {
	    //1.创建连接工厂，采用默认用户名/密码
	    ActiveMQConnectionFactory amqcf = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
	    //2.获取连接,抛出JMSException
	    Connection connection = amqcf.createConnection();
	    //3.启动访问
	    connection.start();
	    //4.创建会话，参数之后详述
	    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    //4.创建目的地：队列/主题,javax.jms.Queue而非java.util.Queue
	    Queue queue = session.createQueue(QUEUE_NAME_1);
	    //Destination接口有Queue和Topic两个子接口
	    //5.创建消息消费者
	    MessageConsumer consumer = session.createConsumer(queue);
	    //等待
	    while (true) {
	        TextMessage msg = (TextMessage) consumer.receive(); //一直等待
	        if (msg != null) {
	            System.out.println("receive message:" + msg.getText());
	        } else {
	            break;
	        }
	    }
	    //关闭资源
	    consumer.close();
	}	
}
