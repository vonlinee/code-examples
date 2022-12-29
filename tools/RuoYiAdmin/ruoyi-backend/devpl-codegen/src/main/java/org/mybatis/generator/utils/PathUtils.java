package org.mybatis.generator.utils;

import java.nio.file.InvalidPathException;

/**
 * 路径处理工具类
 */
public class PathUtils {

    public static final String UNIX_PATH_SEPARATOR = "/";
    public static final String WINDOWS_PATH_SEPARATOR = "\\";
    public static final String PLATFORM_PATH_SEPARATOR;

    static {
        final String osname = System.getProperty("os.name").toLowerCase();
        if (osname.contains("windows")) {
            PLATFORM_PATH_SEPARATOR = WINDOWS_PATH_SEPARATOR;
        } else if (osname.contains("linux")) {
            PLATFORM_PATH_SEPARATOR = UNIX_PATH_SEPARATOR;
        } else {
            PLATFORM_PATH_SEPARATOR = UNIX_PATH_SEPARATOR;
        }
    }

    public static String getPath(String first, String... more) {
        return getPath(true, first, more);
    }

    /**
     * 修改自java.nio.file.Paths#get(String, String...)
     * @param first 路径
     * @param more  路径
     * @return
     * @see java.nio.file.Paths#get(String, String...)
     */
    public static String getPath(boolean requireToNormalize, String first, String... more) {
        if (first == null || first.length() == 0) {
            throw new IllegalArgumentException("path cannot be empty");
        }
        String path;
        if (more.length == 0) {
            path = first;
        } else {
            StringBuilder sb = new StringBuilder();
            // 原Paths#get这里：如果first为null，那么会拼上一个"null"，如果为空字符串，则直接拼上一个空字符串
            sb.append(first);
            for (String segment : more) {
                if (!segment.isEmpty()) {
                    sb.append(PLATFORM_PATH_SEPARATOR);
                    sb.append(segment);
                }
            }
            path = sb.toString();
        }
        if (!requireToNormalize) {
            return path;
        }
        // 解析Windos/Unix
        return parseWindowPath(path, true).path;
    }

    // package-private
    // removes redundant slashes and check input for invalid characters
    static String normalizeAndCheckLinuxPath(String input) {
        int n = input.length();
        char prevChar = 0;
        for (int i = 0; i < n; i++) {
            char c = input.charAt(i);
            if ((c == '/') && (prevChar == '/')) return normalizeLinuxPath(input, n, i - 1);
            checkNotNul(input, c);
            prevChar = c;
        }
        if (prevChar == '/') return normalizeLinuxPath(input, n, n - 1);
        return input;
    }

    private static String normalizeLinuxPath(String input, int len, int off) {
        if (len == 0) return input;
        int n = len;
        while ((n > 0) && (input.charAt(n - 1) == '/')) n--;
        if (n == 0) return "/";
        StringBuilder sb = new StringBuilder(input.length());
        if (off > 0) sb.append(input.substring(0, off));
        char prevChar = 0;
        for (int i = off; i < n; i++) {
            char c = input.charAt(i);
            if ((c == '/') && (prevChar == '/')) continue;
            checkNotNul(input, c);
            sb.append(c);
            prevChar = c;
        }
        return sb.toString();
    }

    // Unicode编码中\u0000代表的是空字符，属于控制字符，也叫不可显字符。
    // 这个空字符与空格不同，空格的编号是"\u0020",而"\u0000"就是一个包含一个字符的字符串，长度为1
    // ""是一个空字符串，这个字符串中一个字符也没有，所以长度是0
    // System.out.println("00" + '\u0000' + "00"); //结果是0000
    // System.out.println("00" + '\u0020' + "00"); //结果是00 00
    // \u0000是空字符，并不是空格。空格字符的unicode码是\u0020
    private static void checkNotNul(String input, char c) {
        if (c == '\u0000') {
            throw new InvalidPathException(input, "Nul character not allowed");
        }
    }

    private static int nextNonSlash(String path, int off, int end) {
        while (off < end && isSlash(path.charAt(off))) {
            off++;
        }
        return off;
    }

    /**
     * Remove redundant slashes from the rest of the path, forcing all slashes
     * into the preferred slash.
     */
    private static String normalize(StringBuilder sb, String path, int off) {
        int len = path.length();
        off = nextNonSlash(path, off, len);
        int start = off;
        char lastC = 0;
        while (off < len) {
            char c = path.charAt(off);
            if (isSlash(c)) {
                if (lastC == ' ') {
                    throw new InvalidPathException(path, "Trailing char <" + lastC + ">", off - 1);
                }
                sb.append(path, start, off);
                off = nextNonSlash(path, off, len);
                if (off != len)   // no slash at the end of normalized path
                    sb.append('\\');
                start = off;
            } else {
                if (isInvalidWindowsPathChar(c)) {
                    throw new InvalidPathException(path, "Illegal char <" + c + ">", off);
                }
                lastC = c;
                off++;
            }
        }
        if (start != off) {
            if (lastC == ' ') throw new InvalidPathException(path, "Trailing char <" + lastC + ">", off - 1);
            sb.append(path, start, off);
        }
        return sb.toString();
    }

    /**
     * The result of a parse operation
     */
    static class Result {
        private final WindowsPathType type;

        private final String root;

        private final String path;

        Result(WindowsPathType type, String root, String path) {
            this.type = type;
            this.root = root;
            this.path = path;
        }

        /**
         * The path type
         */
        WindowsPathType type() {
            return type;
        }

        /**
         * The root component
         */
        String root() {
            return root;
        }

        /**
         * The normalized path (includes root)
         */
        String path() {
            return path;
        }

        @Override
        public String toString() {
            return root + " " + path;
        }
    }

    public static void main(String[] args) {
        System.out.println(PathUtils.getPath("///a/\\b", "dcs", "///f"));
    }

    /**
     * Parses the given input as a Windows path.
     * @param requireToNormalize Indicates if the path requires to be normalized
     */
    public static Result parseWindowPath(String input, boolean requireToNormalize) {
        String root = "";
        WindowsPathType type = null;
        int len = input.length();
        int off = 0;
        if (len > 1) {
            char c0 = input.charAt(0), c1 = input.charAt(1);
            int next = 2;
            if (isSlash(c0) && isSlash(c1)) {
                // UNC: We keep the first two slash, collapse all the
                // following, then take the hostname and share name out,
                // meanwhile collapsing all the redundant slashes.
                type = WindowsPathType.UNC;
                off = nextNonSlash(input, next, len);
                next = nextSlash(input, off, len);
                if (off == next) throw new InvalidPathException(input, "UNC path is missing hostname");
                String host = input.substring(off, next);  // host
                off = nextNonSlash(input, next, len);
                next = nextSlash(input, off, len);
                if (off == next) throw new InvalidPathException(input, "UNC path is missing sharename");
                root = "\\\\" + host + "\\" + input.substring(off, next) + "\\";
                off = next;
            } else {
                if (isLetter(c0) && c1 == ':') { // 绝对路径
                    char c2;
                    if (len > 2 && isSlash(c2 = input.charAt(2))) {
                        // avoid concatenation when root is "D:\"
                        if (c2 == '\\') {
                            root = input.substring(0, 3);
                        } else {
                            root = input.substring(0, 2) + '\\';
                        }
                        off = 3;
                        type = WindowsPathType.ABSOLUTE;
                    } else {
                        root = input.substring(0, 2);
                        off = 2;
                        type = WindowsPathType.DRIVE_RELATIVE;
                    }
                }
            }
        }
        if (off == 0) {
            if (len > 0 && isSlash(input.charAt(0))) {
                type = WindowsPathType.DIRECTORY_RELATIVE;
                root = "\\";
            } else {
                type = WindowsPathType.RELATIVE;
            }
        }
        if (requireToNormalize) {
            StringBuilder sb = new StringBuilder(input.length());
            sb.append(root);
            return new Result(type, root, normalize(sb, input, off));
        } else {
            return new Result(type, root, input);
        }
    }

    public static int nextSlash(String path, int off, int end) {
        char c;
        while (off < end && !isSlash(c = path.charAt(off))) {
            if (isInvalidWindowsPathChar(c))
                throw new InvalidPathException(path, "Illegal character [" + c + "] in path", off);
            off++;
        }
        return off;
    }

    public static boolean isSlash(char c) {
        return (c == '\\') || (c == '/');
    }

    /**
     * 字母
     * @param c 字符
     * @return 是否英文字母
     */
    public static boolean isLetter(char c) {
        return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
    }

    // Reserved characters for window path name
    public static final String reservedChars = "<>:\"|?*";

    public static boolean isInvalidWindowsPathChar(char ch) {
        // '\u0020'是空格字符
        return ch < '\u0020' || reservedChars.indexOf(ch) != -1;
    }
}

