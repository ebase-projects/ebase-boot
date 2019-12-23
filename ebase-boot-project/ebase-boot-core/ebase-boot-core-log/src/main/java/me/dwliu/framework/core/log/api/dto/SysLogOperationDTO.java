package me.dwliu.framework.core.log.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日志模块
 *
 * @author liudw
 * @date 2019-11-18 15:04
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogOperationDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    /**
     * 租户编号
     */
    private String tenantCode;

    /**
     * 用户操作类型
     */
    private String type;
    /**
     * 用户操作
     */
    private String operation;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 请求时长(毫秒)
     */
    private Integer requestTime;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 操作IP
     */
    private String remoteIp;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 用户名
     */
    private String creatorName;
}
