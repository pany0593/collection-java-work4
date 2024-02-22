package com.pany.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class AliOssUtil {
    String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
    String bucketName = "west2-work4-pany0593";
    String accessKeyId="LTAI5tHZWeAYsEY9p5hKUFya";
    String accessKeySecret="s6by6ANbwp254DmDGrF3NCKGfF0Yko";
    String url="";

    public String uploadFile(String objectName, InputStream inputStream) throws Exception {
        OSS ossClient = new OSSClientBuilder().build(this.endpoint,accessKeyId,accessKeySecret);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            url="https://"+ bucketName+ "."+ endpoint.substring(endpoint.lastIndexOf("/")+1)+ "/"+ objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}