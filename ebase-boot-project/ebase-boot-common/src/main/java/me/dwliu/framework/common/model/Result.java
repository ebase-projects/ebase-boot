package me.dwliu.framework.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.dwliu.framework.common.code.IResultCode;
import me.dwliu.framework.common.code.SystemResultCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 通用返回实体
 *
 * @author liudw
 * @date 2019-03-21 16:50
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(description = "通用返回实体")
public class Result<T> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码
	 */
	@Schema(description = "状态码")
	private Integer code;

	/**
	 * 消息
	 */
	@Schema(description = "消息")
	private String msg;

	/**
	 * 时间戳
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "时间戳")
	private LocalDateTime timestamp;

	/**
	 * 数据载体
	 */
	@Schema(description = "数据载体")
	@JsonInclude()
	private T data;

	public Result(IResultCode resultCode) {
		this(resultCode, resultCode.getMsg(), null);
	}

	public Result(IResultCode resultCode, String msg) {
		this(resultCode, msg, null);
	}

	public Result(IResultCode resultCode, T data) {
		this(resultCode, resultCode.getMsg(), data);
	}

	public Result(IResultCode resultCode, String msg, T data) {
		this.code = resultCode.getCode();
		this.msg = msg;
		this.timestamp = LocalDateTime.now();
		this.data = data;
	}


	/**
	 * 获取data
	 *
	 * @param result Result
	 * @param <T>    泛型标记
	 * @return 泛型对象
	 */
	public static <T> T getData(Result<T> result) {
		return Optional.ofNullable(result)
			//.filter(r -> r.success)
			.map(x -> x.getData())
			.orElse(null);
	}

	/**
	 * 返回成功
	 *
	 * @param <T> 泛型标记
	 * @return Result
	 */
	public static <T> Result<T> success() {
		return new Result<>(SystemResultCode.SUCCESS);
	}

	/**
	 * 成功-携带数据
	 *
	 * @param data 数据
	 * @param <T>  泛型标记
	 * @return Result
	 */
	public static <T> Result<T> success(T data) {
		return new Result<>(SystemResultCode.SUCCESS, data);
	}

	/**
	 * 根据状态返回成功或者失败
	 *
	 * @param status 状态
	 * @param msg    异常msg
	 * @param <T>    泛型标记
	 * @return Result
	 */
	public static <T> Result<T> status(boolean status, String msg) {
		return status ? Result.success() : Result.fail(msg);
	}

	/**
	 * 根据状态返回成功或者失败
	 *
	 * @param status 状态
	 * @param sCode  异常code码
	 * @param <T>    泛型标记
	 * @return Result
	 */
	public static <T> Result<T> status(boolean status, IResultCode sCode) {
		return status ? Result.success() : Result.fail(sCode);
	}

	/**
	 * 返回失败信息，用于 web
	 *
	 * @param msg 失败信息
	 * @param <T> 泛型标记
	 * @return {Result}
	 */
	public static <T> Result<T> fail(String msg) {
		return new Result<>(SystemResultCode.FAILURE, msg);
	}

	/**
	 * 返回失败信息
	 *
	 * @param rCode 异常枚举
	 * @param <T>   泛型标记
	 * @return {Result}
	 */
	public static <T> Result<T> fail(IResultCode rCode) {
		return new Result<>(rCode);
	}

	/**
	 * 返回失败信息
	 *
	 * @param rCode 异常枚举
	 * @param msg   失败信息
	 * @param <T>   泛型标记
	 * @return {Result}
	 */
	public static <T> Result<T> fail(IResultCode rCode, String msg) {
		return new Result<>(rCode, msg);
	}

	/**
	 * 返回失败信息-携带数据
	 *
	 * @param rCode 异常枚举
	 * @param msg   失败信息
	 * @param data  数据
	 * @param <T>   泛型标记
	 * @return {Result}
	 */
	public static <T> Result<T> fail(IResultCode rCode, String msg, T data) {
		return new Result<>(rCode, msg, data);
	}
}
