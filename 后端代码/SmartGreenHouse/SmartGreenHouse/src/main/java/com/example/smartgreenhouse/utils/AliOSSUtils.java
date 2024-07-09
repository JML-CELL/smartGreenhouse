package com.example.smartgreenhouse.utils;

/**
 * @author :Kr
 * @date :2024/3/8 15:41
 */
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import java.io.ByteArrayInputStream;

import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云OSS文件上传工具类
 */
@Component
public class AliOSSUtils {

    @Value("${aliOSS-utils.endpoint}")
    private String endpoint;    //服务端点
    @Value("${aliOSS-utils.bucketName}")
    private String bucketName;  //bucket名称
    @Value("${aliOSS-utils.packageName01}")
    private String packageName01;   //对象存储二级包路径
    @Value("outmodel/")
    private String packageName02;
    public String upload(MultipartFile file) throws Exception {

        String endpoint="https://oss-cn-hangzhou.aliyuncs.com";
        String bucketName="smart-green-home";
        String packageName01="image/";
        // 从环境变量中获取RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "LTAI5tQy8uwY3qaD2Yhp3cNR";
        String accessKeySecret = "AJbUzvpgFfkJjfbhxPIo4AKq45m22N";
        // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        //获取上传文件的输入流
        InputStream inputStream = file.getInputStream();

        //使用UUID通用唯一识别码 + 后缀名的形式 防止文件名重复导致覆盖
        String originalFileName = file.getOriginalFilename();
        //增加断言 null值抛出异常

        assert originalFileName != null;

        String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        //创建对象名称（对象存储的位置）
        String objectName = packageName01+fileName;

        //存储文件访问路径
        String url = null;

        //上传文件到OSS
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            //拼接文件访问路径并返回  在endpoint名称中加入bucket名称 最后拼接上文件名
            url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;

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

            inputStream.close();

            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    public String uploadWord(MultipartFile file) throws IOException {
        // 从环境变量中获取RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "LTAI5tQy8uwY3qaD2Yhp3cNR";
        String accessKeySecret = "AJbUzvpgFfkJjfbhxPIo4AKq45m22N";
        // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        //获取上传文件的输入流
        InputStream inputStream = file.getInputStream();

        //使用UUID通用唯一识别码 + 后缀名的形式 防止文件名重复导致覆盖
        String originalFileName = file.getOriginalFilename();
        //增加断言 null值抛出异常
        assert originalFileName != null;

        String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        //创建对象名称（对象存储的位置）
        String objectName = packageName02+fileName;

        //存储文件访问路径
        String url = null;

        //上传文件到OSS
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            //拼接文件访问路径并返回  在endpoint名称中加入bucket名称 最后拼接上文件名
            url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;

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

            inputStream.close();

            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
    public void test()
    {
        String endpoint="https://oss-cn-hangzhou.aliyuncs.com";
        String bucketName="smart-green-home";
        String packageName01="image/";
        // 从环境变量中获取RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "LTAI5tQy8uwY3qaD2Yhp3cNR";
        String accessKeySecret = "AJbUzvpgFfkJjfbhxPIo4AKq45m22N";
        // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            System.out.println("这是"+objectListing);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {

                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() + ")");
            }
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
    }
}
