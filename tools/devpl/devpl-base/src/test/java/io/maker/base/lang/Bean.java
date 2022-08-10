package io.maker.base.lang;

import java.util.Date;

import io.maker.base.collection.ParamMap;

public class Bean {

    protected Bean(Object target) {

    }

    public static void main(String[] args) {
        ParamMap paramMap = new ParamMap();
        paramMap.put("name", "孙允珠");
        paramMap.put("age", 30);
        paramMap.put("sex", "女");
        paramMap.put("description", new Date());
        System.out.println(paramMap);

        System.out.println(paramMap.getString("age"));
    }
}
