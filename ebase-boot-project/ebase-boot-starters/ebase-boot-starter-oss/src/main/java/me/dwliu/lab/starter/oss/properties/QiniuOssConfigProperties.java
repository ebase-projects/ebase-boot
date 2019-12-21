package me.dwliu.lab.starter.oss.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 七牛存储配置
 * <p>
 * 参考：
 * <a href="https://developer.qiniu.com/kodo/sdk/1239/java#install-by-maven">
 * https://developer.qiniu.com/kodo/sdk/1239/java#install-by-maven</a>
 * </p>
 *
 * @author liudw
 * @date 2019-03-16 16:54
 **/
@Getter
@Setter
public class QiniuOssConfigProperties {
    /**
     * 绑定的域名
     */
    private String endpoint;
    /**
     * ACCESS_KEY
     * <p>获取地址：<a href="https://portal.qiniu.com/user/key">
     * https://portal.qiniu.com/user/key</a></p>
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
    ;

    /**
     * 区域名称：z0 华东  z1 华北  z2 华南  na0 北美  as0 东南亚
     */
    private String zone;
}
