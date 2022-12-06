package io.devpl.codegen.fxui.framework.fxml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FXMLScanner {
    private static final List<Object> scanFiles = new ArrayList<>();
    private static int count = 0;

    public static void main(String[] args) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        doScan("D:\\Workspace");
        System.out.println(scanFiles.size());
        System.out.println(count);
        long currentTimeMillis2 = System.currentTimeMillis();
        System.out.println(currentTimeMillis2 - currentTimeMillis);
    }

    /**
     * 扫描所有的FXML文件，Key为相对路径，Value为绝对路径
     */
    private static final Map<String, String> fxml = new LinkedHashMap<>();

    public static String getFxmlLocation(String relativePath) {
        return fxml.get(relativePath);
    }

    /**
     * 从类路径下扫描所有的FXML文件
     */
    public static void scanFXML() {
        new Thread(() -> {

        }).start();
    }

    /**
     * TODO:递归扫描指定文件夹下面的指定文件
     */
    public static void doScan(String folderPath) throws FileNotFoundException {
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            return;
        }
        if (directory.isDirectory()) {
            File[] filelist = directory.listFiles();
            if (filelist == null || filelist.length == 0) {
                return;
            }
            for (int i = 0; i < filelist.length; i++) {
                // 如果当前是文件夹，进入递归扫描文件夹
                if (filelist[i].isDirectory()) {
                    // 递归扫描下面的文件夹
                    count++;
                    doScan(filelist[i].getAbsolutePath());
                } else {  // 非文件夹
                    scanFiles.add(filelist[i].getAbsolutePath());
                    System.out.println(filelist[i].getAbsolutePath());
                }
            }
        }
    }
}
