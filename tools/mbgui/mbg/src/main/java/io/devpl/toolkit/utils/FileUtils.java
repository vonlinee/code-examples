package io.devpl.toolkit.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Collection;

public class FileUtils {

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
                Files.createFile(path);
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 创建多级目录，捕获异常
     *
     * @param dir 目录
     * @return 是否成功
     */
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
     * @param directory 目录
     * @param recursive 是否递归搜索
     * @return 目录下的所有文件
     */
    public static Collection<File> listFiles(String directory, boolean recursive) {
        return org.apache.commons.io.FileUtils.listFiles(new File(directory), null, recursive);
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

    /**
     * 获取文件名
     *
     * @param pathname 路径名称
     * @return 文件名
     */
    public static String getFilename(String pathname) {
        int index = pathname.lastIndexOf(".");
        int indexOfSeparator = pathname.lastIndexOf(File.separator);
        if (index > 0) {
            if (indexOfSeparator > 0) {
                return pathname.substring(indexOfSeparator, index + 1);
            }
            return pathname.substring(0, index + 1);
        } else {
            if (indexOfSeparator > 0) {
                return pathname.substring(indexOfSeparator);
            }
            return pathname;
        }
    }

    /**
     * 文件是否存在
     *
     * @param path 绝对路径
     * @return 文件是否存在
     */
    public static boolean exist(String path) {
        return new File(path).exists();
    }

    public static long copy(InputStream in, Path target) {
        try {
            return Files.copy(in, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
