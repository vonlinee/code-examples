package io.devpl.toolkit.fxui.utils;

import org.apache.commons.io.IOExceptionList;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FileUtils {

    private static final File[] EMPTY_FILES = new File[0];

    @NotNull
    public static File[] listAllFiles(File dir) {
        if (!isDirectory(dir)) {
            return EMPTY_FILES;
        }
        final File[] files = dir.listFiles();
        if (files == null) {
            return EMPTY_FILES;
        }
        return files;
    }

    public static boolean isDirectory(File file) {
        return file != null && file.isDirectory();
    }

    public static List<File> filter(File[] files, FileFilter fileFilter) {
        final List<File> fileList = new ArrayList<>(files.length);
        for (File file : files) {
            if (fileFilter != null && fileFilter.accept(file)) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * Converts an array of file extensions to suffixes for use
     * with IOFileFilters.
     * @param extensions an array of extensions. Format: {"java", "xml"}
     * @return an array of suffixes. Format: {".java", ".xml"}
     */
    private static String[] toSuffixes(final String... extensions) {
        final String[] suffixes = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            suffixes[i] = "." + extensions[i];
        }
        return suffixes;
    }

    public static void show(File file) {
        try {
            // Desktop.getDesktop().browse(new File(projectFolder).toURI());
            Desktop.getDesktop()
                    .open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static URL asURL(File file) {
        try {
            return file.toURI()
                    .toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String slashify(String path, boolean isDirectory) {
        String p = path;
        if (File.separatorChar != '/') p = p.replace(File.separatorChar, '/');
        if (!p.startsWith("/")) p = "/" + p;
        if (!p.endsWith("/") && isDirectory) p = p + "/";
        return p;
    }

    /**
     * Makes a directory, including any necessary but nonexistent parent
     * directories. If a file already exists with specified name but it is
     * not a directory then an IOException is thrown.
     * If the directory cannot be created (or the file already exists but is not a directory)
     * then an IOException is thrown.
     * @param directory directory to create, must not be {@code null}.
     * @throws IOException       if the directory was not created along with all its parent directories.
     * @throws IOException       if the given file object is not a directory.
     * @throws SecurityException See {@link File#mkdirs()}.
     */
    public static File forceMkdir(final File directory) throws IOException {
        return mkdirs(directory);
    }

    public static boolean forceMkdir(File... dirs) throws IOException {
        for (File dir : dirs) {
            forceMkdir(dir);
        }
        return true;
    }

    public static boolean forceMkdir(Path... dirs) throws IOException {
        for (Path dir : dirs) {
            forceMkdir(dir.toFile());
        }
        return true;
    }

    public static boolean forceMkdir(List<Path> dirs) throws IOException {
        for (Path dir : dirs) {
            forceMkdir(dir.toFile());
        }
        return true;
    }

    /**
     * Calls {@link File#mkdirs()} and throws an exception on failure.
     * @param directory the receiver for {@code mkdirs()}, may be null.
     * @return the given file, may be null.
     * @throws IOException       if the directory was not created along with all its parent directories.
     * @throws IOException       if the given file object is not a directory.
     * @throws SecurityException See {@link File#mkdirs()}.
     * @see File#mkdirs()
     */
    private static File mkdirs(final File directory) throws IOException {
        if ((directory != null) && (!directory.mkdirs() && !directory.isDirectory())) {
            throw new IOException("Cannot create directory '" + directory + "'.");
        }
        return directory;
    }

    /**
     * 删除目录
     * @param file
     */
    public static void delete(File file) {
        org.apache.commons.io.FileUtils.deleteQuietly(file);
    }

    /**
     * Cleans a directory without deleting it.
     * @param directory directory to clean
     * @throws NullPointerException     if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if directory does not exist or is not a directory.
     * @throws IOException              if an I/O error occurs.
     * @see #forceDelete(File)
     */
    public static void cleanDirectory(final File directory) throws IOException {
        final File[] files = listFiles(directory, null);

        final List<Exception> causeList = new ArrayList<>();
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                causeList.add(ioe);
            }
        }

        if (!causeList.isEmpty()) {
            // throw new IOExceptionList(directory.toString(), causeList);
            throw new IOExceptionList(causeList);
        }
    }

    /**
     * Deletes a file or directory. For a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * </p>
     * <ul>
     * <li>The directory does not have to be empty.</li>
     * <li>You get an exception when a file or directory cannot be deleted.</li>
     * </ul>
     * @param file file or directory to delete, must not be {@code null}.
     * @throws NullPointerException  if the file is {@code null}.
     * @throws FileNotFoundException if the file was not found.
     * @throws IOException           in case deletion is unsuccessful.
     */
    public static void forceDelete(final File file) throws IOException {
        Objects.requireNonNull(file, "file");
        final Counters.PathCounters deleteCounters;
        try {
            deleteCounters = PathUtils.delete(file.toPath());
        } catch (final IOException e) {
            throw new IOException("Cannot delete file: " + file, e);
        }

        if (deleteCounters.getFileCounter()
                .get() < 1 && deleteCounters.getDirectoryCounter()
                .get() < 1) {
            // didn't find a file to delete.
            throw new FileNotFoundException("File does not exist: " + file);
        }
    }

    private static File[] listFiles(final File directory, final FileFilter fileFilter) throws IOException {
        requireDirectoryExists(directory, "directory");
        final File[] files = fileFilter == null ? directory.listFiles() : directory.listFiles(fileFilter);
        if (files == null) {
            // null if the directory does not denote a directory, or if an I/O error occurs.
            throw new IOException("Unknown I/O error listing contents of directory: " + directory);
        }
        return files;
    }

    /**
     * Requires that the given {@code File} exists and is a directory.
     * @param directory The {@code File} to check.
     * @param name      The parameter name to use in the exception message in case of null input.
     * @return the given directory.
     * @throws NullPointerException     if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the given {@code File} does not exist or is not a directory.
     */
    private static File requireDirectoryExists(final File directory, final String name) {
        requireExists(directory, name);
        requireDirectory(directory, name);
        return directory;
    }

    /**
     * Requires that the given {@code File} exists and throws an {@link IllegalArgumentException} if it doesn't.
     * @param file          The {@code File} to check.
     * @param fileParamName The parameter name to use in the exception message in case of {@code null} input.
     * @return the given file.
     * @throws NullPointerException     if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the given {@code File} does not exist.
     */
    private static File requireExists(final File file, final String fileParamName) {
        Objects.requireNonNull(file, fileParamName);
        if (!file.exists()) {
            throw new IllegalArgumentException("File system element for parameter '" + fileParamName + "' does not exist: '" + file + "'");
        }
        return file;
    }

    /**
     * Requires that the given {@code File} is a directory.
     * @param directory The {@code File} to check.
     * @param name      The parameter name to use in the exception message in case of null input or if the file is not a directory.
     * @return the given directory.
     * @throws NullPointerException     if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the given {@code File} does not exist or is not a directory.
     */
    private static File requireDirectory(final File directory, final String name) {
        Objects.requireNonNull(directory, name);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Parameter '" + name + "' is not a directory: '" + directory + "'");
        }
        return directory;
    }
}
