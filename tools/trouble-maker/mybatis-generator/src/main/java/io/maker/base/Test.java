package io.maker.base;

import java.io.IOException;

import io.maker.base.lang.type.Value;

public class Test {
    public static void main(String[] args) throws IOException {

    	Value value = Value.wrap("A");
    	
    	boolean primitive = Integer.class.isPrimitive();
    	
    	System.out.println(int.class.isPrimitive());
    	
    	
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
