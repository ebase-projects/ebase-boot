package me.dwliu.lab.common.validator;

import me.dwliu.lab.common.exception.BusinessException;
import me.dwliu.lab.common.result.Result;

/**
 * 自定义的参数校验不通过异常
 *
 * @author liudw
 * @date 2019-08-18 17:04
 **/
public class CustomMethodArgumentNotValidException extends BusinessException {
    public CustomMethodArgumentNotValidException(Result<Object> result) {
        super(result);
    }
}
