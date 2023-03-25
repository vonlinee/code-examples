package io.devpl.toolkit.utils;

/**
 * 决定各种文件应该放在哪个位置
 */
public interface ProjectLayout {

    String getAbsolutePath(int fileType);
}
