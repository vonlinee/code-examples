package io.devpl.codegen.fxui.frame;

import javafx.stage.Stage;

import java.util.WeakHashMap;

public class StageManager {

    //存放所有的Stage实例
    private static final WeakHashMap<String, Stage> stageCache = new WeakHashMap<>();

    public static void cache(String name, Stage stage) {
        stageCache.put(name, stage);
    }

    public static Stage getStage(String name) {
        return stageCache.get(name);
    }

}
