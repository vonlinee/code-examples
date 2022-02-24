package io.maker.base;

import io.maker.base.lang.NamedValue;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class Test {
    public static void main(String[] args) throws IOException {
        //String i = ObjectUtils.safeCast(new Object(), String.class);
        //System.out.println(i);
        //String sss = FileUtils.readFileToString(new File("1.txt"));
        //FileUtils.writeString(new File("1.txt"), sss);
//
//        NamedValue namedValue = new NamedValue(null, "A");
//
//        System.out.println(namedValue);
//
//        System.out.println(namedValue.type());
    	
    	
    	Timestamp from = Timestamp.from(Instant.now());
    	System.out.println(from.toString());
    	
    }
}
