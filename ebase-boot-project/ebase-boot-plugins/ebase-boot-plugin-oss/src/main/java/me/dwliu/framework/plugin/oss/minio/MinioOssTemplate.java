package me.dwliu.framework.plugin.oss.minio;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import lombok.SneakyThrows;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.core.oss.rule.OssRule;
import me.dwliu.framework.core.oss.template.OssWithBucketTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * minio 存储
 *
 * @author liudw
 * @date 2019-11-05 20:30
 **/
// @AllArgsConstructor
public class MinioOssTemplate implements OssWithBucketTemplate {

    private String endpoint;
    private String bucketName;
    private MinioClient minioClient;
    private OssRule ossRule;

    public MinioOssTemplate(String endpoint, String bucketName, MinioClient minioClient, OssRule ossRule) {
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.minioClient = minioClient;
        this.ossRule = ossRule;
    }

    @Override
    @SneakyThrows
    public void makeBucket(String bucketName) {
        this.bucketName = bucketName;
        if (!bucketExists(getBucketName(bucketName))) {
            minioClient.makeBucket(getBucketName(bucketName));
            minioClient.setBucketPolicy(getBucketName(bucketName), getPolicyType(getBucketName(bucketName), PolicyTypeEnum.READ));

        }
    }

    @Override
    @SneakyThrows
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(getBucketName(bucketName));

    }

    @Override
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(getBucketName(bucketName));
    }

    @Override
    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName) {
        copyFile(getBucketName(destBucketName), fileName, destBucketName, fileName);
    }

    @Override
    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
        minioClient.copyObject(getBucketName(destBucketName), destFileName, null, null,
                getBucketName(bucketName), fileName, null, null);

    }

    @Override
    @SneakyThrows
    public FileInfo statFile(String bucketName, String fileName) {
        ObjectStat objectStat = minioClient.statObject(getBucketName(bucketName), fileName);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(StringUtils.isBlank(objectStat.name()) ? fileName : objectStat.name());

        if (StringUtils.isBlank(bucketName)) {
            fileInfo.setFileUrl(filePath(fileInfo.getFileName()));
            fileInfo.setFileHostUrl(fileUrl(fileInfo.getFileName()));
        } else {
            fileInfo.setFileUrl(filePath(bucketName, fileInfo.getFileName()));
            fileInfo.setFileHostUrl(fileUrl(bucketName, fileInfo.getFileName()));
        }

        fileInfo.setHash(String.valueOf(objectStat.hashCode()));
        fileInfo.setFileSize(objectStat.length());
        fileInfo.setUploadDate(objectStat.createdTime());
        fileInfo.setContentType(objectStat.contentType());
        return fileInfo;
    }

    @Override
    public FileInfo statFile(String fileName) {
        return statFile(getBucketName(), fileName);
    }

    @Override
    public String filePath(String bucketName, String fileName) {
        return getBucketName(bucketName)
                .concat("/")
                .concat(fileName);
    }

    @Override
    public String filePath(String fileName) {
        return getBucketName()
                .concat("/")
                .concat(fileName);
    }

    @Override
    public String fileUrl(String bucketName, String fileName) {
        return endpoint
                .concat("/")
                .concat(getBucketName(bucketName))
                .concat("/")
                .concat(fileName);
    }

    @Override
    public String fileUrl(String fileName) {
        return endpoint
                .concat("/")
                .concat(getBucketName())
                .concat("/")
                .concat(fileName);
    }

    @Override
    @SneakyThrows
    public FileInfo putFile(String bucketName, String fileName, InputStream stream) {
        makeBucket(bucketName);

        // 提前获取文件大小
        long available = (long) stream.available();

        minioClient.putObject(getBucketName(bucketName), fileName, stream,
                available, null, null, "application/octet-stream");

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileName);
        fileInfo.setOriginalName(fileName);
        if (StringUtils.isBlank(bucketName)) {
            fileInfo.setFileUrl(filePath(fileName));
            fileInfo.setFileHostUrl(fileUrl(fileName));
        } else {
            fileInfo.setFileUrl(filePath(bucketName, fileName));
            fileInfo.setFileHostUrl(fileUrl(bucketName, fileName));
        }

        fileInfo.setFileSize(available);
        fileInfo.setUploadDate(new Date());

        return fileInfo;
    }

    @Override
    @SneakyThrows
    public FileInfo putFile(String bucketName, String fileName, MultipartFile file) {
        return putFile(bucketName, fileName, file.getInputStream());
    }

    @Override
    @SneakyThrows
    public FileInfo putFile(String fileName, InputStream stream) {
        return putFile(getBucketName(), fileName, stream);
    }

    @Override
    @SneakyThrows
    public FileInfo putFile(MultipartFile file) {
        return putFile(getBucketName(), file.getOriginalFilename(), file.getInputStream());
    }

    @Override
    @SneakyThrows
    public FileInfo putFile(String fileName, MultipartFile file) {
        return putFile(getBucketName(), fileName, file.getInputStream());
    }

    @Override
    @SneakyThrows
    public void removeFile(String fileName) {
        minioClient.removeObject(getBucketName(), fileName);
    }

    @Override
    @SneakyThrows
    public void removeFile(String bucketName, String fileName) {
        minioClient.removeObject(getBucketName(bucketName), fileName);
    }

    @Override
    public void removeFiles(List<String> fileNames) {
        minioClient.removeObjects(getBucketName(), fileNames);
    }

    @Override
    public void removeFiles(String bucketName, List<String> fileNames) {
        minioClient.removeObjects(getBucketName(bucketName), fileNames);
    }

    /**
     * 根据规则生成存储桶名称规则
     *
     * @return String
     */
    public String getBucketName() {
        return getBucketName(bucketName);
    }

    /**
     * 根据规则生成存储桶名称规则
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    public String getBucketName(String bucketName) {
        return ossRule.bucketName(bucketName);
    }
//
//    /**
//     * 根据规则生成文件名称
//     *
//     * @param fileName 文件名称
//     * @return String
//     */
//    private String getFileName(String fileName) {
//        return ossRule.fileName(fileName, OssFileNameFormatEnum.NONE);
//    }
//
//    private String getFileName(String fileName, OssFileNameFormatEnum format) {
//        return ossRule.fileName(fileName, format);
//    }

    /**
     * 获取存储桶策略
     *
     * @param bucketName 存储桶名称
     * @param policyType 策略枚举
     * @return String
     */
    public static String getPolicyType(String bucketName, PolicyTypeEnum policyType) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": [\n");

        switch (policyType) {
            case WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            case READ_WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucket\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            default:
                builder.append("                \"s3:GetBucketLocation\"\n");
                break;
        }

        builder.append("            ],\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("\"\n");
        builder.append("        },\n");
        if (PolicyTypeEnum.READ.equals(policyType)) {
            builder.append("        {\n");
            builder.append("            \"Action\": [\n");
            builder.append("                \"s3:ListBucket\"\n");
            builder.append("            ],\n");
            builder.append("            \"Effect\": \"Deny\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::");
            builder.append(bucketName);
            builder.append("\"\n");
            builder.append("        },\n");

        }
        builder.append("        {\n");
        builder.append("            \"Action\": ");

        switch (policyType) {
            case WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            case READ_WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:GetObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            default:
                builder.append("\"s3:GetObject\",\n");
                break;
        }

        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("/*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");
        System.out.println(builder.toString());
        return builder.toString();
    }
}
