package io.spring.boot.rabbitmq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

//@Service
//public class RabbitMQServiceImpl implements RabbitMQService {
//	// 日期格式化
//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//	@Resource
//	private RabbitTemplate rabbitTemplate;
//
//	@Override
//	public String sendMsg(String msg) throws Exception {
//		try {
//			String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
//			String sendTime = sdf.format(new Date());
//			Map<String, Object> map = new HashMap<>();
//			map.put("msgId", msgId);
//			map.put("sendTime", sendTime);
//			map.put("msg", msg);
//			rabbitTemplate.convertAndSend(RabbitMQConfig.RABBITMQ_DEMO_DIRECT_EXCHANGE,
//					RabbitMQConfig.RABBITMQ_DEMO_DIRECT_ROUTING, map);
//			return "ok";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//		}
//	}
//}