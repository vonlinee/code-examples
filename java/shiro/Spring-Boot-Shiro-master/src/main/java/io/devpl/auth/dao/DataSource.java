package io.devpl.auth.dao;

import java.util.HashMap;
import java.util.Map;

public class DataSource {
    private static final Map<String, Map<String, String>> data = new HashMap<>();

    private static final Map<String, UserInfo> userTable = new HashMap<>();

    boolean insert(UserInfo userInfo) {
        return userTable.put(userInfo.getUsername(), userInfo) == null;
    }


    static {
        Map<String, String> data1 = new HashMap<>();
        data1.put("password", "smith123");
        data1.put("role", "user");
        data1.put("permission", "view");

        Map<String, String> data2 = new HashMap<>();
        data2.put("password", "danny123");
        data2.put("role", "admin");
        data2.put("permission", "view,edit");

        data.put("smith", data1);
        data.put("danny", data2);
    }

    public static Map<String, Map<String, String>> getData() {
        return data;
    }
}
