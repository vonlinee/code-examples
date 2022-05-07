package sample.redis.spring.boot.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
	
	public void delay(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
