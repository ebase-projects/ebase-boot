package me.dwliu.framework.starter.datascope.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据权限参数配置类
 *
 * @author liudw
 * @date 2020/10/29 18:02
 **/
@Data
@ConfigurationProperties(prefix = "ebase.data-scope")
public class DataScopeProperties {

    /**
     * 数据权限等级
     * <p>
     * ALL 全部，默认
     * OWN 自己
     * </P>
     */
    private String dataScopeLevel = "ALL";

}
