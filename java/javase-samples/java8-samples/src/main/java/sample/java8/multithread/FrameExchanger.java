package sample.java8.multithread;

import java.awt.*;

public class FrameExchanger {

    private long framesStoredCount = 0;
    private long framesTakenCount = 0;

    private boolean hasNewFrame = false;

    private Frame frame = null;

    // called by Frame producing thread
    public void storeFrame(Frame frame) {
        this.frame = frame;
        this.framesStoredCount++;
        this.hasNewFrame = true;
    }

    // called by Frame drawing thread
    public Frame takeFrame() {
        while (!hasNewFrame) {
            // busy wait until new frame arrives
        }
        Frame newFrame = this.frame;
        this.framesTakenCount++;
        this.hasNewFrame = false;
        return newFrame;
    }
}