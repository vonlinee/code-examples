package io.devpl.codegen.mbpg.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {

    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static String toJSONString(Object obj) {
        return gson.toJson(obj);
    }
}
