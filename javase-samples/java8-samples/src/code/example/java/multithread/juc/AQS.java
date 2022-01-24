package code.example.java.multithread.juc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AQS {
	public static void main(String[] args) {
		// new AQS();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String executeTime = "2022-01-24 00:00:00";
		LocalDateTime time = LocalDateTime.parse(executeTime, formatter);
		
	}
}
