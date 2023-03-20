package io.devpl.toolkit.utils;

import java.io.IOException;

public class CommandRunner {

    public static void excCommand(String new_dir) {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"cmd.exe", "/c", "start"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
