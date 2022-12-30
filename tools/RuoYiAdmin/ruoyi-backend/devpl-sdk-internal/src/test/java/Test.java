import io.devpl.sdk.collection.ArrayMap;
import io.devpl.sdk.DataClass;
import io.devpl.sdk.DataObject;
import io.devpl.sdk.collection.Lists;

import java.util.Map;

public class Test {

    public static void main(String[] args) {
        final DataObject obj = DataClass.newObject();
        obj.put("name", "zs")
           .put("age", 30)
           .put("sex", "女")
           .put("country", "Korea");

        final Map<String, Object> map = obj.asMap();

        ArrayMap<String, Object> map1 = (ArrayMap<String, Object>) map;

        map1.removeAll(Lists.linkOf("name", "age"));

        System.out.println(map1);
    }
}
