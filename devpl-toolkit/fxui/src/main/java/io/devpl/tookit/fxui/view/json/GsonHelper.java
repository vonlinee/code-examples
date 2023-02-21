package io.devpl.tookit.fxui.view.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import org.hjson.JsonValue;
import org.hjson.Stringify;

import java.io.FileReader;
import java.io.IOException;

public class GsonHelper {

    static Gson gson = new Gson();

    public static JsonElement parseFile(String file) {
        try (JsonReader reader = gson.newJsonReader(new FileReader(file))) {
            return gson.fromJson(reader, JsonElement.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String json = "{\n" + "  \"Code\": 200,\n" + "  \"Msg\": \"成功\",\n" + "  \"Data\": [\n" + "    {\n" + "      \"type\": 7,    //  类型  5电子班牌  6门禁  7服务器  8摄像头  9电子楼牌  10闸机\n" + "      \"useCount\": 0  //  使用次数\n" + "    }\n" + "  ]\n" + "}";

    public static JsonElement parseString(String jsonString) {
        return gson.fromJson(jsonString, JsonElement.class);
    }

    public static void main(String[] args) {

    }
}
