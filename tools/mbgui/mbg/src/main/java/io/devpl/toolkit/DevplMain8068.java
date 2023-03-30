package io.devpl.toolkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.compiler.CompiledST;

import java.io.File;

@SpringBootApplication
public class DevplMain8068 {
    public static void main(String[] args) {
//        try {
//            SpringApplication.run(DevplMain8068.class, args);
//            System.out.println("http://localhost:8068/");
//        } catch (Throwable exception) {
//            exception.printStackTrace();
//        }
        File file = new File(new File("").getAbsoluteFile(), "/mbg/src/main/resources/template/test.stg");

        STGroupFile stgf = new STGroupFile(file.getAbsolutePath());

        ST st = stgf.getInstanceOf("method");

        st.add("methodName", "111111111");
        st.add("returnValue", "22222222222");

        System.out.println(st.render());
    }
}
