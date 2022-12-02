package com.lancoo.supervisionplatform;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTest {

    @Test
    public void test3() {
        List<String> list= new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("2");

        List<String> collect = list.stream().filter(s -> s.endsWith("2")).collect(Collectors.toList());

        System.out.println(list);
        System.out.println(collect);

        String s = "a/b/1.jpg";
        System.out.println(FilenameUtils.getName(s));
        System.out.println(s.substring(s.lastIndexOf("/") + 1));
    }
}
