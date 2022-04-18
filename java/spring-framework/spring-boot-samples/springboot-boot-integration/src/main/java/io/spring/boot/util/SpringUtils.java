package io.spring.boot.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware, InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtils.class);
	
	private ApplicationContext applicationContext;
	
	private static File file = new File(System.getProperty("user.home") + File.separator + "1.txt");

	static {
		// 防止非web应用使用AWT API报错
		System.setProperty("java.awt.headless", "false");
		if (!file.exists()) {
			try {
				Files.createFile(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static final PrintStream SYSTEM_OUT = System.out;
	private static final SpringUtils INSTANCE = new SpringUtils();

	private SpringUtils() {
		LOGGER.info("");
	}

	public static void showMyBatisComponent() {
		StringBuilder sb = new StringBuilder();
		SqlSessionFactory sqlSessionFactory = getApplicationContext().getBean(SqlSessionFactory.class);
		if (sqlSessionFactory != null) {
			Configuration configuration = sqlSessionFactory.getConfiguration();
			List<Interceptor> interceptors = configuration.getInterceptors();
			for (Interceptor interceptor : interceptors) {
				sb.append(interceptor.toString());
			}
		}
		writeText(sb.toString());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		INSTANCE.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return INSTANCE.applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public static void disableConsoleOutput() {
		// 当前运行的类名
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		try {
			System.setOut(new DisalbeConsolePrinStream("1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void resetSystemConsoleOutput() {
		System.setOut(System.out);
	}

	private static class DisalbeConsolePrinStream extends PrintStream {
		
		public DisalbeConsolePrinStream(String fileName) throws FileNotFoundException {
			super(fileName);
		}

		@Override
		public void flush() {
			super.flush();
		}

		@Override
		public void close() {
			super.close();
		}

		@Override
		public boolean checkError() {
			return super.checkError();
		}

		@Override
		protected void setError() {
			super.setError();
		}

		@Override
		protected void clearError() {
			super.clearError();
		}

		@Override
		public void write(int b) {
			super.write(b);
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			super.write(buf, off, len);
		}

		@Override
		public void print(boolean b) {
			super.print(b);
		}

		@Override
		public void print(char c) {
			super.print(c);
		}

		@Override
		public void print(int i) {
			super.print(i);
		}

		@Override
		public void print(long l) {
			super.print(l);
		}

		@Override
		public void print(float f) {
			super.print(f);
		}

		@Override
		public void print(double d) {
			super.print(d);
		}

		@Override
		public void print(char[] s) {
			super.print(s);
		}

		@Override
		public void print(String s) {
			super.print(s);
		}

		@Override
		public void print(Object obj) {
			super.print(obj);
		}

		@Override
		public void println() {
			super.println();
		}

		@Override
		public void println(boolean x) {
			super.println(x);
		}

		@Override
		public void println(char x) {
			super.println(x);
		}

		@Override
		public void println(int x) {
			super.println(x);
		}

		@Override
		public void println(long x) {
			super.println(x);
		}

		@Override
		public void println(float x) {
			super.println(x);
		}

		@Override
		public void println(double x) {
			super.println(x);
		}

		@Override
		public void println(char[] x) {
			super.println(x);
		}

		@Override
		public void println(String x) {
			super.println(x);
		}

		@Override
		public void println(Object x) {
			super.println(x);
		}

		@Override
		public PrintStream printf(String format, Object... args) {
			return super.printf(format, args);
		}

		@Override
		public PrintStream printf(Locale l, String format, Object... args) {
			return super.printf(l, format, args);
		}

		@Override
		public PrintStream format(String format, Object... args) {
			return super.format(format, args);
		}

		@Override
		public PrintStream format(Locale l, String format, Object... args) {
			return super.format(l, format, args);
		}

		@Override
		public PrintStream append(CharSequence csq) {
			return super.append(csq);
		}

		@Override
		public PrintStream append(CharSequence csq, int start, int end) {
			return super.append(csq, start, end);
		}

		@Override
		public PrintStream append(char c) {
			return super.append(c);
		}

		@Override
		public void write(byte[] b) throws IOException {
			super.write(b);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		protected void finalize() throws Throwable {
			super.finalize();
		}
	}
	
	private static void writeText(String text) {
		try {
			FileUtils.write(file, text, StandardCharsets.UTF_8);
			openTxtFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void openTxtFile() {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}