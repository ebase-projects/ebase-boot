package me.dwliu.framework.core.security.component;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.code.SystemResultCode;
import me.dwliu.framework.common.model.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 用于tokan校验失败返回信息
 *
 * @author liudw
 * @date 2019-08-16 11:05
 **/
@Slf4j
@Component
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result<String> result = new Result<>();
        result.setCode(SystemResultCode.FAILURE_CODE);
        if (authException != null) {
            result.setMsg(authException.getMessage());
            result.setData(authException.getMessage());
        }
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
