package io.devpl.codegen.ui.fxui.utils;

import io.devpl.codegen.common.utils.Validator;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;

/**
 * 扫描类路径下的FXML文件
 */
public class FXMLScanner {

    private static final String FXML_EXTENSION = ".fxml";

    private static final Log log = LogFactory.getLog(FXMLScanner.class);

    public static Map<String, String> scan() {
        Map<String, String> map = new HashMap<>();
        URL rootClasspath = Thread.currentThread().getContextClassLoader().getResource("");
        try {
            if (rootClasspath != null) {
                URI uri = rootClasspath.toURI();
                String rootDirAbsolutePath = uri.getRawPath().substring(1).replace("/", "\\");
                File file = new File(uri);
                List<File> fxmlFound = new ArrayList<>();
                // 递归 变量路径下面所有的 class文件
                listFiles(file, fxmlFound);
                for (int i = 0; i < fxmlFound.size(); i++) {
                    String absolutePath = fxmlFound.get(i).getAbsolutePath();
                    map.put(getRelativePath(absolutePath, rootDirAbsolutePath), absolutePath);
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void scan(String location) {
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(location);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if ("file".equals(url.getProtocol())) {
                    List<File> fxmlFound = new ArrayList<>();
                    // 递归 变量路径下面所有的 class文件
                    listFiles(new File(url.getFile()), fxmlFound);

                    System.out.println(fxmlFound);
                }
                if ("jar".endsWith(url.getProtocol())) {
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    // 从此jar包 得到一个枚举类
                    Enumeration<JarEntry> entries = urlConnection.getJarFile().entries();
                    // 遍历jar
                    while (entries.hasMoreElements()) {
                        // 获取jar里的一个实体 可以是目录和一些jar包里的其他文件 如META-INF等文件
                        JarEntry entry = entries.nextElement();
                        //得到该jar文件下面的类实体
                        if (entry.getName().endsWith(FXML_EXTENSION)) {
                            // 因为scan 就是/  ， 所有把 file的 / 转成  \   统一都是：  /
                            String fPath = entry.getName().replaceAll("\\\\", "/");
                            // 把 包路径 前面的 盘符等 去掉
                            String packageName = fPath.substring(fPath.lastIndexOf(""));
                            // 去掉后缀.class ，并且把 / 替换成 .    这样就是  com.hadluo.A 格式了 ， 就可以用Class.forName加载了
                            packageName = packageName.replace(".class", "").replaceAll("/", ".");
                            // 根据名称加载类
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> scanFxmls(File rootDir) {
        List<File> res = new ArrayList<>();
        listFiles(rootDir, res);
        return res;
    }

    /**
     * 查找所有的文件 * * @param dir 路径 * @param fileList 文件集合
     */
    private static void listFiles(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : Validator.whenNull(dir.listFiles())) {
                listFiles(f, fileList);
            }
        } else {
            if (dir.getName().endsWith(FXML_EXTENSION)) {
                fileList.add(dir);
                if (log.isDebugEnabled()) {
                    log.debug("find fxml => " + dir.getAbsolutePath());
                }
            }
        }
    }

    private static String getRelativePath(String absPath, String rootParent) {
        return absPath.replace(rootParent, "");
    }
}
