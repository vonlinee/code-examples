package org.example.jvm.runtime.stringtable;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vonline
 * @since 2022-07-27 11:20
 */
public class StringTest1 {

    /**
     * jdk6中：
     * -XX:PermSize=6m -XX:MaxPermSize=6m -Xms6m -Xmx6m
     * <p>
     * jdk8中：
     * -XX:MetaspaceSize=9m -XX:MaxMetaspaceSize=9m -Xms6m -Xmx6m
     */
    @Test
    public void test1() {
        //使用Set保持着常量池引用，避免full gc回收常量池行为
        Set<String> set = new HashSet<>();
        //取值的范围内足以让6MB的PermSize或heap产生OOM了。
        long i = 0;
        while (set.size() < Integer.MIN_VALUE >>> 2 >>> 2 >>> 2) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
