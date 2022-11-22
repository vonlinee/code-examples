package io.sample.obs.config;

/**
 * 封装OBS操作，同时兼容不同的云存储产品
 */
public interface ObsService {

    boolean delete(String bucket, String objectKey);

    /**
     * 删除文件夹
     * @param bucketName
     * @param directoryName
     */
    void deleteDirectory(String bucketName, String directoryName);

    String getSingedUrl(String bucketName, String objectKey);
}
