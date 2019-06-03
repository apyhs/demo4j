package demo.aliyun;

import artoria.file.FileUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 阿里云 OSS 官网文档相关代码测试
 * @author Kahle
 */
public class OSSDemo {
    private static Logger log = LoggerFactory.getLogger(OSSDemo.class);
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限
    // 建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维
    // 请登录 https://ram.console.aliyun.com 创建。
    private static String accessKeyId = "accessKeyId";
    private static String secretAccessKey = "secretAccessKey";
    private static String bucketName = "bucketName";

    @Test
    public void uploadInputStream() throws IOException {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        // 上传文件流。
        InputStream inputStream = new FileInputStream("E:\\Temp\\test.jpg");
        String key = "test/uploadInputStream" + System.currentTimeMillis();
        ossClient.putObject(bucketName, key, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void uploadInputStreamWithMetadata() throws IOException {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        // 创建元数据对象
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        // 上传文件流。
        InputStream inputStream = new FileInputStream("E:\\Temp\\test.jpg");
        String key = "test/uploadInputStreamWithMetadata" + System.currentTimeMillis();
        ossClient.putObject(bucketName, key, inputStream, metadata);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void uploadFile() throws IOException {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        String key = "test/uploadFile" + System.currentTimeMillis();
        ossClient.putObject(bucketName, key, new File("E:\\Temp\\test.jpg"));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void fileDownload() throws IOException {
        String objectName = "test/uploadInputStreamWithMetadata1558493312266";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        // 读取文件内容。
        InputStream objectContent = ossObject.getObjectContent();
        FileUtils.write(objectContent, new File("E:\\Temp\\" + objectName + ".jpg"));
        // 关闭OSSObject。
        ossObject.close();
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void provisionalAuthority() throws IOException {
        // https://help.aliyun.com/document_detail/32016.html
        String objectName = "test/uploadInputStreamWithMetadata1558493312266";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        log.info(url.toString());
    }

    @Test
    public void doSignature() throws IOException {
        // host 的格式为 bucketname.endpoint
        String host = "http://" + bucketName + "." + endpoint;
        // callbackUrl 为上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        String callbackUrl = "http://88.88.88.88:8888";
        // 用户上传文件时指定的前缀。
        String dir = "user-dir-prefix/";
        // 创建OSSClient实例。
        OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);

        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        java.sql.Date expiration = new java.sql.Date(expireEndTime);

        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);

        Map<String, String> respMap = new LinkedHashMap<>();
        respMap.put("accessid", accessKeyId);
        respMap.put("policy", encodedPolicy);
        respMap.put("signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("host", host);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));

        Map<String, String> callbackMap = new LinkedHashMap<>();
        callbackMap.put("callbackUrl", callbackUrl);
        callbackMap.put("callbackBody",
                "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callbackMap.put("callbackBodyType", "application/x-www-form-urlencoded");
        String base64CallbackBody = BinaryUtil.toBase64String(JSON.toJSONString(callbackMap).getBytes());
        respMap.put("callback", base64CallbackBody);

//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "GET, POST");

        log.info("Signature: {}", JSON.toJSONString(respMap, true));
    }

}
