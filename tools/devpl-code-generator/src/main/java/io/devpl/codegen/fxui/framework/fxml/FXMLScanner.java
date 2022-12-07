package io.devpl.codegen.fxui.framework.fxml;

import io.devpl.codegen.fxui.utils.Resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FXMLScanner {
    private static final List<File> scanFiles = new ArrayList<>();

    private static final String parentPath = "static/fxml";

    static int i = 0;

    public static void scanClassPath() {
        final URL classpathRoot = Resources.getAppClassLoader().getResource(parentPath);

        if (classpathRoot == null) {
            return;
        }

        try {
            final File rootDirectory = new File(classpathRoot.toURI());

            final String absoluteRootPath = rootDirectory.getAbsolutePath().replace("\\", "/");

            i = absoluteRootPath.indexOf(parentPath);

            doScan(rootDirectory.getAbsolutePath());
            for (File scanFile : scanFiles) {
                fxmlLocations.put(scanFile.getCanonicalPath(), scanFile);
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 扫描所有的FXML文件，Key为相对路径，Value为绝对路径
     */
    private static final Map<String, File> fxmlLocations = new LinkedHashMap<>();

    public static File getFxmlFile(String relativePath) {
        return fxmlLocations.get(relativePath);
    }

    static final FilenameFilter FXML_FILTER = (dir, name) -> name != null && name.endsWith(".fxml");

    /**
     * TODO:递归扫描指定文件夹下面的指定文件
     */
    public static void doScan(String folderPath) throws FileNotFoundException {
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            return;
        }
        if (directory.isDirectory()) {
            File[] files = directory.listFiles(FXML_FILTER);
            if (files == null || files.length == 0) {
                return;
            }
            for (File file : files) {
                // 如果当前是文件夹，进入递归扫描文件夹
                if (file.isDirectory()) {
                    // 递归扫描下面的文件夹
                    doScan(file.getAbsolutePath());
                } else {  // 非文件夹
                    //
                    fxmlLocations.put(file.getAbsolutePath().substring(i).replace("\\", "/"), file);
                }
            }
        }
    }

    public static void main(String[] args) {
        FXMLScanner.scanClassPath();

        System.out.println(fxmlLocations);
    }
}
