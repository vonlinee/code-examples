package org.example.jvm.gc.algorithm;

// -XX:+PrintGCDetails
public class RefCountGC {
    //占用一点内存，方便查看
    private byte[] bigSize = new byte[5 * 1024 * 1024];//5MB
    Object reference = null;

    public static void main(String[] args) {
        RefCountGC obj1 = new RefCountGC();
        RefCountGC obj2 = new RefCountGC();
        // 互相赋值
        obj1.reference = obj2;
        obj2.reference = obj1;
        obj1 = null;
        obj2 = null;
        //显式的执行垃圾回收，这里obj1和obj2能否被回收？
        System.gc();
    }
}