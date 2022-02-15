package code.fxutils.support.util;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    public static boolean exists(File file) {
        return file.exists();
    }

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

    public static FileInputStream openInputStream(final File file) throws IOException {
        Objects.requireNonNull(file, "file");
        return new FileInputStream(file);
    }

    public static String readFileToString(File file) {
        return "";
    }

    private class TextLineIterator implements Iterator<String> {
        private String line;
        private BufferedReader reader;

        public TextLineIterator(File file) {
            try (FileReader fr = new FileReader("")) {
                reader = new BufferedReader(fr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean hasNext() {
            if (line == null) {
                closeQuitely(reader);
                return false;
            }
            return true;
        }

        @Override
        public String next() {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static void closeQuitely(Closeable... closeableList) {
        for (Closeable target : closeableList) {
            try {
                target.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TODO 优化内存占用
     * @param rootPath 根目录
     * @return List
     */
    public static List<File> listFiles(String rootPath, FileFilter filter) {
        List<File> resultList = new ArrayList<>();
        File file = new File(rootPath); // 获取当前文件夹
        if (!file.exists()) {
            return new ArrayList<>(0);
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return new ArrayList<>(0);
        }
        File[] directorys = file.listFiles(); // 获取根目录下所有文件与文件夹
        if (Validator.isNullOrEmpty(directorys)) {
            return new ArrayList<>(0);
        }
        LinkedList<File> directoryList = new LinkedList<>(Arrays.asList(directorys));
        while (directoryList.size() > 0) { // 文件集合中若存在数据，则继续循环
            File first = directoryList.removeFirst();
            if (first.exists()) {
                if (first.isDirectory()) {
                    directoryList.addAll(Arrays.asList(Objects.requireNonNull(first.listFiles())));
                }
                //不管文件或目录，根据传入的过滤器选择是否加入结果
                if (filter.accept(first)) {
                    resultList.add(first);
                }
            }
        }
        return resultList;
    }

    public static void deleteProjectFiles(String rootDirectory) {
        FileFilter filter = pathname -> {
            boolean isFile = pathname.isFile();
            boolean isDirectory = pathname.isDirectory();
            boolean exists = pathname.exists();
            if (!exists) return false;
            if (isDirectory) {
                String name = pathname.getName().trim();
                if (name.equals(".idea") || name.equals(".target") || name.equals("bin") || name.equals(".settings")) {
                    return true;
                }
            }
            if (isFile) {
                String name = pathname.getName();
                return name.endsWith(".classpath") || name.endsWith(".iml") || name.endsWith(".project");
            }
            return false;
        };
        List<File> files = listFiles(rootDirectory, filter);
        System.out.println("共找到 " + files.size() + " 个文件");
        files.forEach(file -> {
            boolean delete = file.delete();
            if (delete) {
                System.out.println("删除" + file.getAbsolutePath() + "成功");
            } else {
                System.out.println("删除" + file.getAbsolutePath() + "失败");
            }
        });
    }

    public static void main(String[] args) {
        deleteProjectFiles("D:\\Projects\\Github\\code-example\\spring-cloud-samples\\springcloud-samples-parent");
    }
}
