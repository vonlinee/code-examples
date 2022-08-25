package io.devpl.sdk.internal.rest;

import com.google.gson.Gson;
import org.joda.beans.DynamicBean;
import org.joda.beans.DynamicMetaBean;
import org.joda.beans.impl.flexi.FlexiBean;
import org.joda.beans.impl.map.MapBean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class JodaBeanTest {
    Gson gson = new Gson();

    @Test
    public void test1() throws NoSuchFieldException {
        MapBean mapBean = new MapBean();
        mapBean.propertyDefine("name", String.class);
        mapBean.propertyDefine("age", int.class);
        mapBean.put("name", "zs");
        mapBean.put("age", 200);
        System.out.println(gson.toJson(mapBean));
        System.out.println(mapBean.get("name"));

        DynamicMetaBean metaBean = mapBean.metaBean();


        Field field = JodaBeanTest.class.getField("name");


    }

    @Test
    public void test2() {
        FlexiBean bean = new FlexiBean();

        bean.propertyDefine("name", String.class);
        bean.set("name", "zs");
        String value = (String) bean.get("name");
        System.out.println(value);

        DynamicMetaBean metaBean = bean.metaBean();

        Class<? extends DynamicBean> clazz = metaBean.beanType();

        // Field name = clazz.getField("name");

        System.out.println(clazz);
    }
}
