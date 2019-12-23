package me.dwliu.framework.starter.oss.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * aliyun 存储
 *
 * @author liudw
 * @date 2019-11-08 21:26
 **/
@Getter
@Setter
public class AliyunOssConfigProperties {

    /**
     * 绑定的域名
     */
    private String endpoint;

    /**
     * ACCESS_KEY
     * <p>
     *     https://usercenter.console.aliyun.com/#/manage/ak
     * </p>
     *
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
     * 区域名称
     */
    private String zone;

}
