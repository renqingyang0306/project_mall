package com.cskaoyan.project.mall.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/20 14:08
 */
@ConfigurationProperties(prefix = "myoss")
@Component
public class MyOssClient {
    String bucket;
    String endpoint ;
    String accessKey ;
    String secret ;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void ossFileUpload(MultipartFile myfile,String uuid) throws IOException {
        InputStream inputStream = myfile.getInputStream();
        long size = myfile.getSize();
        String contentType = myfile.getContentType();

		/*String bucket = "cskaoyan";
		String endpoint = "oss-cn-beijing.aliyuncs.com";
		String accessKey = "LTAI8EgTxlc4QQZr";
		String secret = "RBDvSGZOR8DaUJxlLrW4Ed46RVnAkR";*/

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uuid, inputStream, objectMetadata);

        OSSClient ossClient = new OSSClient(endpoint, accessKey, secret);
        ossClient.putObject(putObjectRequest);
    }
}