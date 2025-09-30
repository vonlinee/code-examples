package org.example.fileupload.controller;

import org.example.fileupload.domain.ChunkFileParam;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @PostMapping("/upload")
    public void upload() {

    }

    @PostMapping("/upload/base64")
    public void upload(@RequestBody String base64) throws IOException {

        String imgByte = base64.replaceAll("data:image/png;base64,", "");
        byte[] imageByte = Base64.getDecoder().decode(imgByte);
        for (int i = 0; i < imageByte.length; ++i) {
            if (imageByte[i] < 0) {// 调整异常数据
                imageByte[i] += (byte) 256;
            }
        }

        Path path = Files.write(Paths.get("D:/Temp/1.jpg"), imageByte, StandardOpenOption.CREATE_NEW);
    }

    /**
     * 分片上传
     *
     * @param param 分片上传参数
     * @throws IOException IO异常
     */
    @PostMapping("/upload/chunk")
    public void uploadChunk(ChunkFileParam param) throws IOException {
        // 分片文件保存目录
        String uploadDir = "D:/Temp/fileUpload";
        // 分片文件名
        String chunkFilename = param.getTaskId() + "_" + param.getChunk();
        try (OutputStream os = Files.newOutputStream(Paths.get(uploadDir, chunkFilename), StandardOpenOption.CREATE_NEW)) {
            StreamUtils.copy(param.getFile().getInputStream(), os);
        }


    }
}
