package io.maker.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.maker.base.io.FileUtils;

@SpringBootApplication
public class CodeGenApplication {
	public static void main(String[] args) throws ClassNotFoundException, LinkageError {
		 // SpringApplication.run(CodeGenApplication.class, args);
		FileUtils.deleteProjectFiles("C:\\Users\\ly-wangliang\\Desktop\\code-samples\\tools\\trouble-maker");
	}
}
