package io.devpl.toolkit.fxui;

import io.devpl.toolkit.fxui.common.MainUI;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.IOException;

import javafx.application.Application;

/**
 * JDK 11 + MyBatis Generator V1.4.1（截止2022-09-12最新版）
 */
public class AppLauncher {

    public static void main(String[] args) throws IOException {
        Application.launch(MainUI.class);
    }
}
