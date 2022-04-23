package io.maker.base.lang;

import io.maker.base.collection.ParamMap;
import io.maker.base.lang.reflect.TypeMetadataHolder;

import java.util.Date;

public class Bean extends TypeMetadataHolder {

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
