package sample.java;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

public class TestStringReader {

	//在字符串缓冲区中收集输出的字符流，可用于构造字符串， 关闭流无效，关闭后调用其他方法不会报异常
	public static void main(String[] args) {
		StringWriter sw1 = new StringWriter();
		StringWriter sw2 = new StringWriter(100);
		
		sw1.append("AAAAAAAAAAAAAAAA");
		sw1.flush();
		
		StringReader sr = new StringReader(sw1.getBuffer().toString());
		
	}
	
	public void method(Exception ex) {
		StringWriter writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer));
        String info = writer.getBuffer().toString();
        info = info.replace("\n", "<br/>&nbsp;");
        info = info.substring(0, 1000);
		//log.setException(info); //log是我实例的一个日志记录的对象，保存这个EX报错信息就可以用这个
	}
}
