package com.sittingseat.sittingseat.shopkeeper.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sittingseat.sittingseat.exception.SittingSeatErrorCode;
import com.sittingseat.sittingseat.exception.SittingSeatException;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3Service {
    private final AmazonS3Client amazonS3Client;
    private final ImageFileService imageFileService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public String uploadFile(MultipartFile multipartFile, String dirName, String subDirName){
        String key = dirName + "/" + subDirName + "/" + UUID.randomUUID() + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        try {
            objMeta.setContentLength(multipartFile.getInputStream().available());
            amazonS3Client.putObject(bucketName, key, multipartFile.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new SittingSeatException(SittingSeatErrorCode.S3_ACCESS_DENIED, e);
        }

        return amazonS3Client.getUrl(bucketName, key).toString();
    }

    @Transactional
    public void uploadFiles(List<MultipartFile> multipartFiles, String dirName, String subDirName, Restaurant restaurant){
        for (MultipartFile multipartFile : multipartFiles) {
            String s3ImagePath = uploadFile(multipartFile, dirName, subDirName);
            imageFileService.saveImage(s3ImagePath, multipartFile.getOriginalFilename(), restaurant);
        }
    }
}
