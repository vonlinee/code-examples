package code.fxutils.generator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import freemarker.template.TemplateException;

public class Test {

    public static void main(String[] args) throws IOException,
            TemplateException {
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	LocalDateTime now = LocalDateTime.now();
    	
    	int i = now.compareTo(now.plusSeconds(24));
    	int j = now.compareTo(now.minusSeconds(14));
//    	System.out.println(i);
//    	System.out.println(j);
    	
    	FreeMarker aFreeMarker = null;
    	
    	method(aFreeMarker);
    	
    }
    
	private static int compareDateTime(LocalDateTime dt1, LocalDateTime dt2, long secondsOffset) {
		int i = dt1.compareTo(dt2.minusSeconds(secondsOffset));
		int j = dt1.compareTo(dt2.plusSeconds(secondsOffset));
		if (i < 0) {
			return i;
		}
		if (j > 0) {
			return j;
		}
		return 0;
	}
	
	private static void method(FreeMarker marker) {
		boolean aa = marker instanceof AbstractTemplateEngine;
	}
	
}