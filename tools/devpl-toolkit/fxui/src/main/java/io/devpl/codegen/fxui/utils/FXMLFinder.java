package io.devpl.codegen.fxui.utils;

import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * https://docs.oracle.com/javafx/2/api/javafx/fxml/doc-files/introduction_to_fxml.html
 */
public class FXMLFinder {

    private final Log log = LogFactory.getLog(FXMLFinder.class);

    private List<String> getClasspath() {
        return Arrays.stream(System.getProperty("java.class.path").split(";")).filter(classpath -> !classpath.endsWith(".jar")).collect(Collectors.toList());
    }

    public Map<String, URI> scanClasspath() {
        Map<String, URI> map = new HashMap<>();
        for (String classpath : getClasspath()) {
            map = scan(classpath, map);
        }
        return map;
    }

    public static Map<String, URI> findFromClasspath() {
        FXMLFinder finder = new FXMLFinder();
        return finder.scanClasspath();
    }

    private Map<String, URI> scan(String path, Map<String, URI> result) {
        if (result == null) {
            result = new HashMap<>();
        }
        for (String classpath : getClasspath()) {
            String rootDirAbsolutePath = classpath.replace("/", "\\");
            // 递归 变量路径下面所有的 class文件
            for (File value : listFiles(new File(classpath))) {
                String absolutePath = value.getAbsolutePath();
                result.put(getRelativePath(absolutePath, rootDirAbsolutePath), value.toURI());
            }
        }
        return result;
    }

    private List<File> listFiles(File dir) {
        List<File> files = new ArrayList<>();
        listFiles(dir, files);
        return files;
    }

    /**
     * 查找所有的文件 * * @param dir 路径 * @param fileList 文件集合
     */
    private void listFiles(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                files = new File[0];
            }
            for (File file : files) {
                listFiles(file, fileList);
            }
        } else {
            if (dir.getName().endsWith(".fxml")) {
                fileList.add(dir);
            }
        }
    }

    private String getRelativePath(String absPath, String rootParent) {
        return absPath.replace(rootParent, "");
    }
}
