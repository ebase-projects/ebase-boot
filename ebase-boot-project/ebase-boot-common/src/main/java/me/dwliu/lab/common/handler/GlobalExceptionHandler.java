package me.dwliu.lab.common.handler;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.lab.common.exception.BusinessException;
import me.dwliu.lab.common.result.Result;
import me.dwliu.lab.common.result.SystemResultCode;
import me.dwliu.lab.common.validator.CustomMethodArgumentNotValidException;
import me.dwliu.lab.common.validator.ParameterInvalidItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的异常处理器
 *
 * @author liudw
 * @date 2019-05-30 11:43
 **/
@RestControllerAdvice
//@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 400 - Bad Request
     * <p>处理验证参数封装错误时异常</p>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return Result.fail("参数解析失败");
    }

    /**
     * 400 - Bad Request
     * <p>处理使用@Validated注解时，参数验证错误异常</p>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("handleMethodArgumentNotValidException start, uri:{}, caused by: {}", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = new ArrayList<>();

        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            ParameterInvalidItem parameterInvalidItem = new ParameterInvalidItem();
            parameterInvalidItem.setFieldName(fieldError.getField());
            parameterInvalidItem.setMessage(fieldError.getDefaultMessage());
            parameterInvalidItemList.add(parameterInvalidItem);
        }

        return Result.fail(SystemResultCode.PARAM_VALID_ERROR, "参数验证错误", parameterInvalidItemList);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);

        return Result.fail("不支持当前请求方法");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("不支持当前媒体类型", e);
        return Result.fail("不支持当前媒体类型");
    }

    /**
     * MissingServletRequestPartException
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result handleMissingServletRequestPartException(Exception e) {
        log.error("必填参数不能为空", e);

        return Result.fail(String.format("必填参数不能为空: %s", e.getMessage()));
    }

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AccessDeniedException.class)
//    public Result accessDeniedException(Exception e) {
//        log.error("没有权限不允许访问", e);
//
//        return Result.fail("没有权限不允许访问");
//    }

    /**
     * 自定义 400 - Bad Request
     * <p>处理使用@Validated注解时，参数验证错误异常</p>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    public Result handleRRException(CustomMethodArgumentNotValidException e) {
        if (e.getResult() != null) {
            return e.getResult();
        }
        return Result.fail(e.getMessage());

    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleRRException(BusinessException e) {
        if (e.getResult() != null) {
            return e.getResult();
        }
        return Result.fail(e.getMessage());

    }


    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        Result Result = new Result();
        Result.setMsg("系统未捕获异常，请联系管理员");
        Result.setCode(SystemResultCode.FAILURE_CODE);

        return Result;
    }
}
