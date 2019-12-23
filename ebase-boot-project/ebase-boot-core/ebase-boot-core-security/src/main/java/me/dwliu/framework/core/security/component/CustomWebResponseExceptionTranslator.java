package me.dwliu.framework.core.security.component;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.security.exception.CustomOAuth2Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * TODO 异常处理,重写oauth 默认实现
 *
 * @author liudw
 * @date 2019-08-16 10:44
 **/
@Slf4j
//public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
public class CustomWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new CustomOAuth2Exception(oAuth2Exception.getMessage()));
    }
}
