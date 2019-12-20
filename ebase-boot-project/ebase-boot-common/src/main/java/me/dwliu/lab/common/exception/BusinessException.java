package me.dwliu.lab.common.exception;

import lombok.Getter;
import me.dwliu.lab.common.result.IResultCode;
import me.dwliu.lab.common.result.Result;

/**
 * 自定义业务异常
 *
 * @author liudw
 * @date 2019-03-21 17:57
 **/
@Getter
public class BusinessException extends RuntimeException {

	private final Result<Object> result;

	public BusinessException(Result<Object> result) {
		super(result.getMsg());
		this.result = result;
	}

	public BusinessException(IResultCode rCode) {
		this(rCode, rCode.getMsg());
	}

	public BusinessException(IResultCode rCode, String message) {
		super(message);
		this.result = Result.fail(rCode, message);
	}

	public BusinessException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		doFillInStackTrace();
		this.result = null;
	}

	/**
	 * 提高性能
	 *
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}
}
