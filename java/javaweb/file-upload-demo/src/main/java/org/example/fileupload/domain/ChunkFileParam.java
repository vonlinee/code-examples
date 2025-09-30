package org.example.fileupload.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChunkFileParam {
    private String taskId;      // 唯一标识一个文件上传任务
    private MultipartFile file; // 分片文件
    private String md5;         // 当前分片的md5
    private Integer chunk;      // 当前分片号
    private Integer chunks;     // 总分片数
    private Long start;         // 分片开始字节
    private Long end;           // 分片结束字节
    private String filename;    // 文件名称
    private Long totalSize;     // 文件总大小, 单位/字节
    private Long chunkSize;     // 分片大小, 单位/字节
}
