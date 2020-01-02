package me.dwliu.framework.core.datascope.exception;

import me.dwliu.framework.common.code.IResultCode;
import me.dwliu.framework.common.exception.BusinessException;
import me.dwliu.framework.common.model.Result;

/**
 * 数据权限异常
 *
 * @author liudw
 * @date 2020-01-01 22:34
 **/
public class DataScopeException extends BusinessException {

	public DataScopeException(Result<Object> result) {
		super(result);
	}

	public DataScopeException(IResultCode rCode) {
		super(rCode);
	}

	public DataScopeException(IResultCode rCode, String message) {
		super(rCode, message);
	}

	public DataScopeException(String message, Throwable cause) {
		super(message, cause);
	}
}
