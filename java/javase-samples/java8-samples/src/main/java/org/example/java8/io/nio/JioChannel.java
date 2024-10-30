package org.example.java8.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class JioChannel {

    public static void main(String[] args) {

    }

    // 零拷贝方式文件复制
    public void zeroCopy(String from, String to) throws IOException {
        try (FileChannel source = FileChannel.open(Paths.get(from));
             FileChannel destination = FileChannel.open(Paths.get(to))) {
            source.transferTo(0, source.size(), destination);
        }
    }

    // 传统方式文件复制
    public void copy(String from, String to) throws IOException {
        byte[] data = new byte[8 * 1024];
        File fromFile = new File(from);
        long bytesToCopy = fromFile.length();
        long bytesCopied = 0;
        try (FileInputStream fis = new FileInputStream(fromFile);
             FileOutputStream fos = new FileOutputStream(to)) {
            while (bytesCopied < bytesToCopy) {
                int bytesRead = fis.read(data);
                fos.write(data);
                bytesCopied += data.length;
            }
            fos.flush();
        }
    }
}