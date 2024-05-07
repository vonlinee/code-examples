package org.example.java8.io;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * <p>
 * Title: TestLoger2
 * </p>
 * <p>
 * Description:
 * </p>
 * @author Administrator
 * @date 2019年5月18日
 */

public class TestLoger2 {

	private static Logger logger = Logger.getLogger(TestLoger2.class.getName());

	public void test05() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			int i = 1 / 0;
		} catch (Exception e) {
			FileHandler fileHandler = new FileHandler("D:\\Log\\JDKLog1" + "\\" + sdf.format(new Date()) + ".log",
					true);
			logger.addHandler(fileHandler);// 日志输出文件
			// logger.addHandler(new ConsoleHandler());//输出到控制台
			// logger.setLevel(Level.ALL);
			fileHandler.setFormatter(new SimpleFormatter());// 输出格式
			logger.log(Level.INFO, e.toString());
		}
	}
}