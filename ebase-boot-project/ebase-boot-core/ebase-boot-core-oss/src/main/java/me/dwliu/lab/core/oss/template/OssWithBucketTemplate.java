package me.dwliu.lab.core.oss.template;

import me.dwliu.lab.core.oss.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 文件上传接口
 *
 * @author liudw
 * @date 2019-11-05 17:03
 **/
public interface OssWithBucketTemplate extends OssTemplate {

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    void makeBucket(String bucketName);

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶名称
     */
    void removeBucket(String bucketName);

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return
     */
    boolean bucketExists(String bucketName);

    /**
     * 拷贝文件
     *
     * @param bucketName     存储桶名称
     * @param fileName       存储桶文件名称
     * @param destBucketName 目标存储桶名称
     */
    void copyFile(String bucketName, String fileName, String destBucketName);

    /**
     * 拷贝文件
     *
     * @param bucketName     存储桶名称
     * @param fileName       存储桶文件名称
     * @param destBucketName 目标存储桶名称
     * @param destFileName   目标存储桶文件名称
     */
    void copyFile(String bucketName, String fileName, String destBucketName, String destFileName);


    /**
     * 获取文件信息
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return
     */
    FileInfo statFile(String bucketName, String fileName);

    /**
     * 获取文件相对路径
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return
     */
    String filePath(String bucketName, String fileName);

    /**
     * 获取文件地址
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return
     */
    String fileUrl(String bucketName, String fileName);

    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @param stream     文件流
     * @return
     */
    FileInfo putFile(String bucketName, String fileName, InputStream stream);

    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @param file       上传文件类
     * @return
     */
    FileInfo putFile(String bucketName, String fileName, MultipartFile file);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     */
    void removeFile(String bucketName, String fileName);

    /**
     * 批量删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileNames  文件名称集合
     */
    void removeFiles(String bucketName, List<String> fileNames);


    /**
     * 根据规则生成存储桶名称规则
     *
     * @return String
     */
    String getBucketName();

    /**
     * 根据规则生成存储桶名称规则
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    String getBucketName(String bucketName);
}
