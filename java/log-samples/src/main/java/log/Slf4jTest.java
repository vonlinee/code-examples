package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
	static final Logger log = LoggerFactory.getLogger(Slf4jTest.class);
	
	public static void main(String[] args) {
		log.info("=================");
	}
}
