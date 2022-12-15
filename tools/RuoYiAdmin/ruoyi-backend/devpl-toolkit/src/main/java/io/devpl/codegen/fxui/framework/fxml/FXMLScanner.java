package io.devpl.codegen.fxui.framework.fxml;

import io.devpl.codegen.fxui.utils.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FXMLScanner {

    private static final String parentPath = "static/fxml";

    private static int i = 0;

    public static Map<String, String> scan() {
        FilenameFilter filter = (dir, name) -> name != null && name.endsWith(".fxml");
        final URL classpathRoot = ResourceUtils.getClassLoader().getResource(parentPath);
        if (classpathRoot == null) {
            return Collections.emptyMap();
        }
        try {
            final File rootDirectory = new File(classpathRoot.toURI());
            final String absoluteRootPath = rootDirectory.getAbsolutePath().replace("\\", "/");
            i = absoluteRootPath.indexOf(parentPath);
            Map<String, String> map = new LinkedHashMap<>();
            doScan(rootDirectory.getAbsolutePath(), filter, map);
            return map;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO:递归扫描指定文件夹下面的指定文件
     */
    private static void doScan(String folderPath, final FilenameFilter filter, final Map<String, String> result) throws FileNotFoundException {
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            return;
        }
        if (directory.isDirectory()) {
            File[] files = directory.listFiles(filter);
            if (files == null || files.length == 0) {
                return;
            }
            for (File file : files) {
                // 如果当前是文件夹，进入递归扫描文件夹
                if (file.isDirectory()) {
                    // 递归扫描下面的文件夹
                    doScan(file.getAbsolutePath(), filter, result);
                } else {  // 非文件夹
                    //
                    final String absolutePath = file.getAbsolutePath().intern();
                    try {
                        result.put(absolutePath.substring(i).replace("\\", "/"), file.toURI().toURL().toExternalForm());
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
