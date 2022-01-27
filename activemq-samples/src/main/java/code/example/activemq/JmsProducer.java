package code.example.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProducer {
	
	private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
	private static final String QUEUE_NAME_1 = "queue-01";

	public static void main(String[] args) throws JMSException {
	    //1.创建连接工厂，采用默认用户名/密码，admin/admin
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
	    Destination destination = queue;
	    //5.创建消息生产者
	    MessageProducer messageProducer = session.createProducer(destination);
	    //6.发送消息到MQ的队列里
	    for (int i = 0; i < 3; i++) {
	        //创建消息
	        Message message = session.createTextMessage("Message-" + i);
	        messageProducer.send(message);
	    }
	    //关闭资源
	    messageProducer.close();
	    session.close();
	    connection.close();
	}
}
