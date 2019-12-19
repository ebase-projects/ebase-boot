package me.dwliu.lab.core.tool.validator;

import me.dwliu.lab.core.tool.exception.BusinessException;
import me.dwliu.lab.core.tool.result.Result;

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
