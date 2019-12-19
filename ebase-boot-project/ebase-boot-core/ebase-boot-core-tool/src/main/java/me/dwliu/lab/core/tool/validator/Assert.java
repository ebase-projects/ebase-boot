package me.dwliu.lab.core.tool.validator;

import me.dwliu.lab.core.tool.exception.BusinessException;
import me.dwliu.lab.core.tool.result.SystemResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 *
 * @author liudw
 * @email ldw4033 at 163.com
 * @date 2017-03-23 15:50
 */
@Deprecated
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(SystemResultCode.FAILURE, message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(SystemResultCode.FAILURE, message);
        }
    }
}
