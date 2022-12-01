package io.devpl.sdk.beans.test;

import io.devpl.sdk.beans.DynamicMetaBean;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import io.devpl.sdk.beans.impl.map.BeanMap;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FlexiBean bean = new FlexiBean();
        bean.set("name", "age");

        DynamicMetaBean dynamicMetaBean = bean.metaBean();

        BeanMap map = new BeanMap();

        MetaProperty<String> name = dynamicMetaBean.metaProperty("name");
        System.out.println(name.getString(map));
    }
}
