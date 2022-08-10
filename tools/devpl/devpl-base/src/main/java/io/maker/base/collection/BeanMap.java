package io.maker.base.collection;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 对JavaBean的映射
 */
public final class BeanMap extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public <T> T getField(String fieldName) {
        return (T) super.get(fieldName);
    }

    @SuppressWarnings({"unchecked"})
    public <T> T putField(String fieldName, Object fieldValue) {
        return (T) super.put(fieldName, fieldValue);
    }

    @Override
    public Object get(Object key) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    /**
     * 应用反射(其实工具类底层一样用的反射技术)
     * 手动写一个 Bean covert to Map
     */
    public static <T> BeanMap copy(T bean) {
        BeanMap beanMap = new BeanMap();
        try {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                String methodName = method.getName();
                //反射获取属性与属性值的方法很多，以下是其一；也可以直接获得属性，不过获取的时候需要用过设置属性私有可见
                if (methodName.contains("get")) {
                    //invoke 执行get方法获取属性值
                    Object value = method.invoke(bean);
                    //根据setXXXX 通过以下算法取得属性名称
                    String key = methodName.substring(methodName.indexOf("get") + 3);
                    Object temp = key.substring(0, 1).toLowerCase();
                    key = key.substring(1);
                    //最终得到属性名称
                    key = temp + key;
                    beanMap.putField(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanMap;
    }
}
