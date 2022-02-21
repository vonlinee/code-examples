package io.maker.base.io;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static io.maker.base.Regexs.matches;

/**
 * NIO读写文件工具类
 */
public final class UFiles {

    private String file;

    /**
     * 不同平台
     * @param filepath 文件路径
     * @return
     */
    public static boolean exists(String filepath) {
        return Files.exists(Paths.get(filepath), LinkOption.NOFOLLOW_LINKS);
    }

    private static final Pattern linux_path_pattern = Pattern.compile("(/([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255}/)*([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255})|/)");
    private static final Pattern windows_path_pattern = Pattern.compile("(^[A-Z]:((\\\\|/)([a-zA-Z0-9\\-_]){1,255}){1,255}|([A-Z]:(\\\\|/)))");

    /**
     * check path is valid in windows and linux
     * @param path path to be validate
     *             platform valid value: linux,windows
     * @return whether the path is valid
     **/
    public static boolean isPathValid(String path) {
        String osName = System.getProperty("os.name");
        if (osName.equalsIgnoreCase("linux")) {
            return matches(linux_path_pattern, path);
        }
        if (osName.equalsIgnoreCase("windows")) {
            return matches(windows_path_pattern, path);
        }
        return false;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public UFiles(String file) throws IOException {
        super();
        this.file = file;
    }

    /**
     * NIO读取文件
     * @param allocate
     * @throws IOException
     */
    public void read(int allocate) throws IOException {
        RandomAccessFile access = new RandomAccessFile(this.file, "r");
        //FileInputStream inputStream = new FileInputStream(this.file);
        FileChannel channel = access.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
        CharBuffer charBuffer = CharBuffer.allocate(allocate);
        Charset charset = Charset.forName("GBK");
        CharsetDecoder decoder = charset.newDecoder();
        int length = channel.read(byteBuffer);
        while (length != -1) {
            byteBuffer.flip();
            decoder.decode(byteBuffer, charBuffer, true);
            charBuffer.flip();
            System.out.println(charBuffer.toString());
            // 清空缓存
            byteBuffer.clear();
            charBuffer.clear();
            // 再次读取文本内容
            length = channel.read(byteBuffer);
        }
        channel.close();
        access.close();
    }

    /**
     * NIO写文件
     * @param context
     * @param allocate
     * @param chartName
     * @throws IOException
     */
    public void write(String context, int allocate, String chartName) throws IOException {
        // FileOutputStream outputStream = new FileOutputStream(this.file); //文件内容覆盖模式 --不推荐
        FileOutputStream outputStream = new FileOutputStream(this.file, true); //文件内容追加模式--推荐
        FileChannel channel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
        byteBuffer.put(context.getBytes(chartName));
        byteBuffer.flip();//读取模式转换为写入模式
        channel.write(byteBuffer);
        channel.close();
        if (outputStream != null) {
            outputStream.close();
        }
    }

    /**
     * nio事实现文件拷贝
     * @param source
     * @param target
     * @param allocate
     * @throws IOException
     */
    public static void nioCpoy(String source, String target, int allocate) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
        FileInputStream inputStream = new FileInputStream(source);
        FileChannel inChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream(target);
        FileChannel outChannel = outputStream.getChannel();

        int length = inChannel.read(byteBuffer);
        while (length != -1) {
            byteBuffer.flip();//读取模式转换写入模式
            outChannel.write(byteBuffer);
            byteBuffer.clear(); //清空缓存，等待下次写入
            // 再次读取文本内容
            length = inChannel.read(byteBuffer);
        }
        outputStream.close();
        outChannel.close();
        inputStream.close();
        inChannel.close();
    }

    /**
     * 传统方法实现文件拷贝 IO方法实现文件k拷贝
     * @param sourcePath
     * @param destPath
     * @throws Exception
     */
    public static void traditionalCopy(String sourcePath, String destPath) throws Exception {
        File source = new File(sourcePath);
        File dest = new File(destPath);
        if (!dest.exists()) {
            if (dest.createNewFile()) {
                System.out.println("创建文件成功：" + dest.getAbsolutePath());
            }
        }
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fis.close();
        fos.close();
    }

    public static void main(String[] args) throws Exception {
        /*long start = System.currentTimeMillis();
        traditionalCopy("D:\\常用软件\\JDK1.8\\jdk-8u181-linux-x64.tar.gz", "D:\\常用软件\\JDK1.8\\IO.tar.gz");
        long end = System.currentTimeMillis();
        System.out.println("用时为：" + (end-start));*/

        // long start = System.currentTimeMillis();
        // nioCpoy("D:\\常用软件\\JDK1.8\\jdk-8u181-linux-x64.tar.gz", "D:\\常用软件\\JDK1.8\\NIO.tar.gz", 1024);
        // long end = System.currentTimeMillis();

        // System.out.println("用时为：" + (end - start));
        edit(new File("D:/Temp/input.txt"));
    }

    public static boolean openDirectory(File file) {
        if (file.isDirectory()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return openDirectory(getParentFile(file));
    }

    public static void edit(File file) {
        if (file.isFile()) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.EDIT)) {
                        desktop.edit(file);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean openFile(File file) {
        if (file.isFile()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        throw new UnsupportedOperationException(file.getAbsolutePath() + "是目录!");
    }

    /**
     * 使用相对路径创建的File对象没有父级目录
     * System.out.println(new File(""));
     * System.out.println(new File("D:/Temp/1.txt").getParentFile());
     * @param file File
     * @return
     */
    public static File getParentFile(File file) {
        if (file == null || file.isAbsolute()) return file;
        return file.getAbsoluteFile().getParentFile();
    }

    public static boolean isPathAbsolute(File file) {
        if (file == null) return false;
        return file.isAbsolute();
    }
}
