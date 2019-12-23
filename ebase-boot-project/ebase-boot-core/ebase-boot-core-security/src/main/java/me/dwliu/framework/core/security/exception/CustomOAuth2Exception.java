package me.dwliu.framework.core.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2Exception，解决返回异常与系统异常不一致的返回形式问题
 * <p>
 * https://blog.csdn.net/dandandeshangni/article/details/80472147
 *
 * @author liudw
 * @date 2019-08-16 10:10
 **/
@JsonSerialize(using = CustomOAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {
    public CustomOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOAuth2Exception(String msg) {
        super(msg);
    }
}
