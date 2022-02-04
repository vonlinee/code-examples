package code.fxutils.support.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class JsonUtils {

    private static final Gson GSON = new Gson();

    public static String mapStringToJson(String str) {
        int len = str.length();
        String kvlist = str.substring(1, len - 1);
        String[] kvmaps = kvlist.split(",");
        JsonObject jsonObject = new JsonObject();
        for (String kvmap : kvmaps) {
            String[] strings = kvmap.split("=");
            String key = strings[0];
            String value = strings.length == 1 ? "" : strings[1];
            jsonObject.addProperty(key.trim(), value.trim());
        }
        return GSON.toJson(jsonObject);
    }

    public static String mapValueToJson(Map<String, String> map) {
        String str = map.toString();
        int len = str.length();
        String kvlist = str.substring(1, len - 1);
        String[] kvmaps = kvlist.split(",");
        JsonObject jsonObject = new JsonObject();
        for (String kvmap : kvmaps) {
            String[] strings = kvmap.split("=");
            String key = strings[0];
            String value = strings.length == 1 ? "" : strings[1];
            jsonObject.addProperty(key.trim(), value.trim());
        }
        return GSON.toJson(jsonObject);
    }
}