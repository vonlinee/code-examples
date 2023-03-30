package io.devpl.toolkit.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * @param path   路径
     * @param exists 路径是否存在，由调用方决定
     * @return 路径是否为目录
     * @see Files#isDirectory(Path, LinkOption...) 路径不存在时返回false
     */
    public static boolean isDirectory(Path path, boolean exists) {
        return isDirectory(path, exists, false);
    }

    /**
     * 路径是否是目录
     *
     * @param path          路径
     * @param exists        该路径是否真是存在
     * @param isFollowLinks 链接文件
     * @return 是否是目录
     */
    public static boolean isDirectory(Path path, boolean exists, boolean isFollowLinks) {
        if (null == path) {
            return false;
        }
        if (exists) {
            LinkOption[] options = isFollowLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            return Files.isDirectory(path, options);
        }
        // 不存在的文件只能根据路径本身来判断是否是文件
        // Path对象在构造时就会自动适配平台的路径分隔符
        String pathString = path.toString();
        return pathString.endsWith(File.separator);
    }

    /**
     * 创建文件，如果不存在
     *
     * @param pathString 文件路径字符串
     * @return 是否成功
     */
    public static synchronized boolean createIfNotExistsQuitely(String pathString) {
        Path path = Path.of(pathString);
        if (Files.exists(path)) {
            return true;
        }
        if (isDirectory(path, false)) {
            return createDirectoriesQuitely(path);
        }
        return createFile(path);
    }

    /**
     * @param path 路径，指向一个文件，而不是目录
     * @return 是否创建成功
     */
    public static boolean createFile(Path path) {
        if (!Files.exists(path) && createDirectoriesQuitely(path.getParent())) {
            try {
                log.info("创建文件 {}", path);
                Files.createFile(path);
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean createDirectoriesQuitely(String dir) {
        return createDirectoriesQuitely(Path.of(dir));
    }

    /**
     * 创建文件夹
     *
     * @param dir 文件夹
     * @return 是否成功
     */
    public static boolean createDirectoriesQuitely(Path dir) {
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 列出目录下的所有文件
     *
     * @param directory
     * @param recursive
     * @return
     */
    public static File[] listFiles(String directory, boolean recursive) {
        return new File[0];
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名
     */
    public static String getExtension(@NotNull String filename) {
        int index = filename.lastIndexOf(".");
        if (index > 0) {
            return filename.substring(index + 1);
        }
        return "";
    }
}
