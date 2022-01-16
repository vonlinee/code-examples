package code.fxutils.core.util;

import javax.tools.JavaFileManager;
import javax.tools.StandardJavaFileManager;
import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSystemUtils {

    public static void createNewEmptyFile(String directory, String filename) {
        createNewEmptyFile(directory + File.separator + filename);
    }

    public static void createNewEmptyFile(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            return;
        }
        String path = file.getAbsolutePath();
        System.out.println(path);
    }

    public static boolean normalize(String path) {
        File file = new File(path);
        return file.isFile();
    }

    /**
     * 检查文件路径是否合法
     * @param path
     * @return
     */
    public static boolean isIllegalPath(String path) {
        String regex = "[a-zA-Z]:(?:[/\\\\][^/\\\\:*?\"<>|]{1,255})+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }
}
