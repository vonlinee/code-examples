package io.devpl.test;

import org.joda.beans.impl.map.MapBean;

public class Test {

    public static void main(String[] args) {
        final MapBean mapBean = new MapBean();

        mapBean.propertyDefine("name", String.class);

        System.out.println(mapBean);

    }
}
