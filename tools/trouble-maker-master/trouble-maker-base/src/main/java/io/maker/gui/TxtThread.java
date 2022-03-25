package io.maker.gui;

import java.io.File;
import java.io.IOException;

public class TxtThread extends Thread {

    private volatile boolean stop = false;
    private volatile boolean openFile = false;

    private File file;
    private String absolutePath;
    private Process notepadSubThread;

    public void setOpenFile(boolean openFile) {
        this.openFile = openFile;
    }

    public TxtThread(File txtFile) {
        this.file = txtFile == null ? new File("D:/Temp/output.txt") : txtFile;
        this.absolutePath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("创建文件成功：" + absolutePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (openFile) {
                    notepadSubThread = Runtime.getRuntime().exec("notepad " + absolutePath);
                    int value = notepadSubThread.waitFor();
                    System.out.println(value);
                    if (value == 0) {
                        openFile = false;
                    }
                }
                if (getState() == State.TERMINATED) {
                    synchronized (this) {
                        notepadSubThread.destroyForcibly();
                        notepadSubThread = null;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IO error: " + e);
        } catch (InterruptedException e1) {
            System.err.println("Exception: " + e1.getMessage());
        }
    }
}
