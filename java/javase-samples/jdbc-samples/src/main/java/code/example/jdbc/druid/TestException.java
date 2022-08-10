package code.example.jdbc.druid;

import java.io.PrintStream;

public class TestException {

    public static void main(String[] args) {
    	
    	System.out.println(System.err.getClass());
    	PrintStream st = null;
    	
        throwException();
    }

    public static int method() {
        try {
            return method1();
        } catch (Exception exception) {
            return 10;
        }
    }

    public static int method1() {
        return method2();
    }

    
    public static void throwException() {
    	try {
    		int i = 1 / 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n");
			System.out.println(e.toString());
		}
    }
    
    public static int method2() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement);
        }
        return 10;
    }
}