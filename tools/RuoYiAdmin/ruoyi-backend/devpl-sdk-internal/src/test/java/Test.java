import io.devpl.sdk.utils.ArrayMap;
import io.devpl.sdk.utils.Maps;
import org.openjdk.jol.info.ClassLayout;

import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("name", "zs");
        map.put("id", "1");
        map.put("sex", "男");
        System.out.println(ClassLayout.parseInstance(map).toPrintable());

        Map<String, Object> map1 = Maps.builder(new ArrayMap<String, Object>())
                                       .put("name", "")
                                       .put("id", "a")
                                       .build();


    }
}
