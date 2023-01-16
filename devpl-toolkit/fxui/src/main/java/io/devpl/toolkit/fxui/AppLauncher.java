package io.devpl.toolkit.fxui;

import io.devpl.toolkit.fxui.app.MainUI;
import javafx.application.Application;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * JDK 11 + MyBatis Generator V1.4.1（截止2022-09-12最新版）
 */
public class AppLauncher {

    static final Log log = LogFactory.getLog(AppLauncher.class);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Application.launch(MainUI.class);
        log.info("启动耗时:{}ms", System.currentTimeMillis() - start);
    }
}
