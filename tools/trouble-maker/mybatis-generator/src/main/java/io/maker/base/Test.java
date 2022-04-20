package io.maker.base;

import java.io.IOException;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import io.maker.generator.db.DataSourceBuilder;

public class Test {
    public static void main(String[] args) throws IOException {

    	DataSource dataSource = DataSourceBuilder.create()
    		.url("jdbc:mysql://localhost:3306/mysql_learn?useUnicode=true&characterEncoding=utf8")
    		.type(DruidDataSource.class)
    		.username("root")
    		.password("123456")
    		.driverClassName("com.mysql.jdbc.Driver")
    		.build();
    	
    	System.out.println(dataSource);
    	
//        new HashMap<>();
//
//        HashMap<String, Object> fixedMap = new HashMap<>(1);
//        fixedMap.put("A", "A");
//
//        HashMap<String, Object> fixedMap1 = new HashMap<>(0, 1);
//        fixedMap.put("A", "A");
//        int i = 0;

//        List<File> files = FileUtils.listFiles("D:\\Projects\\Github\\code-example\\javase-samples\\java8-samples\\src", new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                return pathname.getName().endsWith(".class");
//            }
//        });
//        files.forEach(file -> {
//            if (file.isFile()) {
//                System.out.println(file.getName());
//                file.delete();
//            }
//        });

        // FileUtils.deleteProjectFiles("D:\\Projects\\Github\\code-samples");

    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
