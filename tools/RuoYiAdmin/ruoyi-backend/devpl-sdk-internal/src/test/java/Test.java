import io.devpl.sdk.util.*;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        DataObject obj = DataClass.newObject();
        obj.put("age", "28");
        obj.put("name", "孙允珠");

        final String name = obj.getValue("name", String.class);

        obj.put("map", DataClass.newObject());

        System.out.println(obj.asMap());

        System.out.println(IdUtils.simpleULID());



    }
}
