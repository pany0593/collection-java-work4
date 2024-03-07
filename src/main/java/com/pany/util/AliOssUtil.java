package com.pany.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.stream.Collectors;

@Component
public class AliOssUtil {
    @Value("${alioss.endpoint}")
    String endpoint;
    @Value("${alioss.bucketName}")
    String bucketName;
    @Value("${alioss.accessKeyId}")
    String accessKeyId;
    @Value("${alioss.accessKeySecret}")
    String accessKeySecret;
    String url="";
    @Autowired
    private SnowFlakeUtils snowFlakeUtils;
    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    public String uploadAvatar(String objectName, InputStream inputStream) throws Exception {
        OSS ossClient = new OSSClientBuilder().build(this.endpoint,accessKeyId,accessKeySecret);
        String userId = (String)threadLocalUtil.get("userId");
        String fileId= String.valueOf(snowFlakeUtils.nextId());
        String objectPostfix=objectName.substring(objectName.lastIndexOf("."));
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "avatar/"+userId+"-"+fileId+objectPostfix,inputStream);
            ossClient.putObject(putObjectRequest);
            url="https://"+ bucketName+ "."+ endpoint.substring(endpoint.lastIndexOf("/")+1)+ "/"+"avatar/"+ userId+"-"+fileId+objectPostfix;
        } catch (OSSException oe) {
            extracted(oe);
        } catch (ClientException ce) {
            extracted(ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    //?
    public void uploadArticle(String articleId, String content)throws OSSException,ClientException{
        OSS ossClient = new OSSClientBuilder().build(this.endpoint,accessKeyId,accessKeySecret);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "article/" + articleId+".txt",new ByteArrayInputStream(content.getBytes()));
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            extracted(oe);
            throw oe;
        } catch (ClientException ce) {
            extracted(ce);
            throw ce;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public String downLoadArticle(String articleId) throws IOException {
        String objectName = "article/" + articleId + ".txt";
        String content = "";
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        try {
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
            ossObject.close();
        } catch (OSSException oe) {
            extracted(oe);
            throw oe;
        } catch (Throwable ce) {
            extracted(ce);
            throw ce;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return content;
    }

    private static void extracted(Throwable ce) {
        System.out.println("Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network.");
        System.out.println("Error Message:" + ce.getMessage());
    }

    private static void extracted(ClientException ce) {
        System.out.println("Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network.");
        System.out.println("Error Message:" + ce.getMessage());
    }

    private static void extracted(OSSException oe) {
        System.out.println("Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason.");
        System.out.println("Error Message:" + oe.getErrorMessage());
        System.out.println("Error Code:" + oe.getErrorCode());
        System.out.println("Request ID:" + oe.getRequestId());
        System.out.println("Host ID:" + oe.getHostId());
    }

}