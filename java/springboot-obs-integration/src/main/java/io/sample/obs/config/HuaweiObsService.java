package io.sample.obs.config;

import com.obs.services.ObsClient;
import com.obs.services.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class HuaweiObsService implements ObsService {

    private final ObsClient obsClient;

    public HuaweiObsService(ObsClient obsClient) {
        this.obsClient = obsClient;
    }

    @Override
    public boolean delete(String bucket, String objectKey) {
        DeleteObjectResult result = obsClient.deleteObject(bucket, objectKey);
        return result.getStatusCode() == 200;
    }

    /**
     * https://bbs.huaweicloud.com/blogs/370929
     * @param bucketName    桶名
     * @param directoryName 目录名称
     */
    @Override
    public void deleteDirectory(String bucketName, String directoryName) {
        ListObjectsRequest request = new ListObjectsRequest(bucketName);
        request.setPrefix(directoryName);
        ObjectListing result;
        // 列举文件夹中所有的对象
        do {
            result = obsClient.listObjects(request);
            request.setMarker(result.getNextMarker());
        } while (result.isTruncated());

        List<ObsObject> resultObjects = result.getObjects();
        // 将文件夹下所有对象信息中objectKey重新组装成一个新的list，这个list中都是objectKey
        List<String> objectKeys = resultObjects.stream().map(ObsObject::getObjectKey).collect(Collectors.toList());

        // 将组装好的keys封装成keyAndVersions对象，然后进一步封装成DeleteObjectsRequest对象
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        objectKeys.forEach(deleteObjectsRequest::addKeyAndVersion);

        DeleteObjectsResult deleteObjectsResult = obsClient.deleteObjects(deleteObjectsRequest);

        deleteObjectsResult.getErrorResults();
    }

    /**
     * 可以通过OBS SDK生成带有鉴权信息的URL，同时指定URL的有效期来限制访问时长，
     * 之后便可将URL提供给其它用户进行临时访问
     * @param bucketName
     * @param objectKey
     * @return
     */
    @Override
    public String getSingedUrl(String bucketName, String objectKey) {
        long expireSeconds = 10L;

        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);

        String signedUrl = response.getSignedUrl();

        return signedUrl;
    }
}
