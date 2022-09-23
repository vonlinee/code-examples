package io.devpl.sdk.restful;


import com.google.gson.Gson;
import org.joda.beans.impl.map.MapBean;
import org.junit.jupiter.api.Test;

public class MapBeanTest {

    Gson gson = new Gson();

    @Test
    public void test1() {
        MapBean mapBean = new MapBean();
        mapBean.propertyDefine("name", String.class);
        mapBean.propertyDefine("age", int.class);
        mapBean.put("name", "zs");
        mapBean.put("age", 200);
        System.out.println(gson.toJson(mapBean));

        System.out.println(mapBean.get("name"));


    }

}
