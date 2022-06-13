package io.maker.test;

import java.io.File;

public class Employee {

    public static void main(String[] args) {

        final File[] files = new File("C:\\Users\\vonline\\Desktop\\code-samples\\java\\mybatis-samples").listFiles(File::isDirectory);

        for (File file : files) {
            System.out.println(file.getName());
        }

    }
}
