package code.example.activemq;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws JMSException {

        ArrayList<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("k1", "v1");
            map.put("k2", "v2");
            list.add(map);
        }

        list.toString();

        System.out.println(list);

    }
}
