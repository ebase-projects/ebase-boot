package me.dwliu.framework.common.validator;

import lombok.Data;

/**
 * 参数校验条目
 *
 * @author liudw
 * @date 2019-08-18 14:18
 **/
@Data
public class ParameterInvalidItem {
    private String message;
    private String fieldName;

}
