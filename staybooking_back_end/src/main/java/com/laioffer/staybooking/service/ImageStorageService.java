package com.laioffer.staybooking.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.laioffer.staybooking.exception.GCSUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageStorageService {
    @Value("${gcs.bucket}")
    //從application.properties讀bucket name
    private String bucketName;

    private Storage storage;
    @Autowired
    public ImageStorageService(Storage storage) {
        this.storage = storage;
    }

    public String save(MultipartFile file) throws GCSUploadException {
        String filename = UUID.randomUUID().toString(); //随机字负串,globally unique
        BlobInfo blobInfo = null;
        try {
            blobInfo = storage.createFrom(
                    BlobInfo
                            .newBuilder(bucketName, filename)
                            .setContentType("image/jpeg")
                            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                            //setAcl,上传文件的时候读取文件的权限向所有的AllUsers打开
                            .build(),
                    file.getInputStream()); //getInputStream 文件变成字节流上传到服务器里
        } catch (IOException exception) {
            throw new GCSUploadException("Failed to upload file to GCS");
        }

        return blobInfo.getMediaLink();
    }
}

