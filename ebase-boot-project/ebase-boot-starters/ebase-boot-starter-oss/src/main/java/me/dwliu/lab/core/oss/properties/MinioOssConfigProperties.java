package me.dwliu.lab.core.oss.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * minio存储配置
 * <p>
 * 参考：<a href="https://docs.min.io/cn/minio-quickstart-guide.html">
 * https://docs.min.io/cn/minio-quickstart-guide.html</a>
 * </p>
 *
 * @author liudw
 * @date 2019-03-16 16:54
 **/
@Getter
@Setter
public class MinioOssConfigProperties {

    /**
     * 绑定的域名
     */
    private String endpoint;

    /**
     * ACCESS_KEY
     */
    private String accessKey;

    /**
     * SECRET_KEY
     */
    private String secretKey;


    /**
     * 存储空间名
     */
    private String bucketName = "default";

    /**
     * 是否启用
     */
    private Boolean enabled;

}
