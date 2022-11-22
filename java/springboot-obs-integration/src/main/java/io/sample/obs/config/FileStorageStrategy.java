package io.sample.obs.config;

/**
 * 上传文件存储策略
 */
public interface FileStorageStrategy {

    /**
     * 决定存放的路径
     * @return
     */
    String getSavePath(String... subPaths);
}
