package me.dwliu.lab.core.tool.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统返回码
 * <pre>
 * 错误编码，由6位数字组成，前3位为模块编码，后3位为业务编码
 * <pre>
 * 如：100001（100代表系统模块，001代表业务代码）
 * </pre>
 *
 * @author liudw
 * @date 2019-03-21 16:15
 **/
@Getter
@AllArgsConstructor
@ApiModel(value = "系统返回码",
	description = "由6位数字组成，前3位为模块编码，后3位为业务编码," +
		"如：100001（100代表系统模块，001代表业务代码")
public enum SystemResultCode implements IResultCode {

	/**
	 * 操作成功
	 */
	SUCCESS(SystemResultCode.SUCCESS_CODE, "操作成功"),

	/**
	 * 系统未知异常
	 */
	FAILURE(SystemResultCode.FAILURE_CODE, "系统未知异常"),

	/**
	 * 缺少必要的请求参数
	 */
	PARAM_MISS(SystemResultCode.PARAM_MISS_CODE, "缺少必要的请求参数"),

	/**
	 * 请求参数类型错误
	 */
	PARAM_TYPE_ERROR(SystemResultCode.PARAM_TYPE_ERROR_CODE, "请求参数类型错误"),

	/**
	 * 请求参数绑定错误
	 */
	PARAM_BIND_ERROR(SystemResultCode.PARAM_BIND_ERROR_CODE, "请求参数绑定错误"),

	/**
	 * 参数校验失败
	 */
	PARAM_VALID_ERROR(SystemResultCode.PARAM_VALID_ERROR_CODE, "参数校验失败"),

	/**
	 * 404 没找到请求
	 */
	NOT_FOUND(SystemResultCode.NOT_FOUND_CODE, "404 没找到请求"),

	/**
	 * 消息不能读取
	 */
	MSG_NOT_READABLE(SystemResultCode.MSG_NOT_READABLE_CODE, "消息不能读取"),

	/**
	 * 不支持当前请求方法
	 */
	METHOD_NOT_SUPPORTED(SystemResultCode.METHOD_NOT_SUPPORTED_CODE, "不支持当前请求方法"),

	/**
	 * 不支持当前媒体类型
	 */
	MEDIA_TYPE_NOT_SUPPORTED(SystemResultCode.MEDIA_TYPE_NOT_SUPPORTED_CODE, "不支持当前媒体类型"),

	/**
	 * 不接受的媒体类型
	 */
	MEDIA_TYPE_NOT_ACCEPT(SystemResultCode.MEDIA_TYPE_NOT_ACCEPT_CODE, "不接受的媒体类型"),

	/**
	 * 请求被拒绝
	 */
	REQ_REJECT(SystemResultCode.REQ_REJECT_CODE, "请求被拒绝"),

	//-----------------------------通用数据层--------------------------------//
	/**
	 * 数据添加失败
	 */
	DATA_NOT_FOUND(SystemResultCode.DATA_NOT_FOUND_CODE, "数据不存在"),

	/**
	 * 数据添加失败
	 */
	DATA_ADD_FAILED(SystemResultCode.DATA_ADD_FAILED_CODE, "数据添加失败"),

	/**
	 * 数据更新失败
	 */
	DATA_UPDATE_FAILED(SystemResultCode.DATA_UPDATE_FAILED_CODE, "数据更新失败"),

	/**
	 * 数据删除失败
	 */
	DATA_DELETE_FAILED(SystemResultCode.DATA_DELETE_FAILED_CODE, "数据删除失败"),
	;


	/**
	 * 通用 code
	 */
	public static final int SUCCESS_CODE = 0;
	public static final int FAILURE_CODE = 1;

	public static final int PARAM_MISS_CODE = 100000;
	public static final int PARAM_TYPE_ERROR_CODE = 100001;
	public static final int PARAM_BIND_ERROR_CODE = 100002;
	public static final int PARAM_VALID_ERROR_CODE = 100003;
	public static final int NOT_FOUND_CODE = 100004;
	public static final int MSG_NOT_READABLE_CODE = 100005;
	public static final int METHOD_NOT_SUPPORTED_CODE = 100006;
	public static final int MEDIA_TYPE_NOT_SUPPORTED_CODE = 100007;
	public static final int MEDIA_TYPE_NOT_ACCEPT_CODE = 100008;
	public static final int REQ_REJECT_CODE = 100009;

	//-----------------------------通用数据层--------------------------------//
	/**
	 * 通用数据层 code
	 */
	public static final int DATA_NOT_FOUND_CODE = 100100;
	public static final int DATA_ADD_FAILED_CODE = 100101;
	public static final int DATA_UPDATE_FAILED_CODE = 100102;
	public static final int DATA_DELETE_FAILED_CODE = 100103;

	/**
	 * 消息码
	 */
	private final Integer code;

	/**
	 * 消息描述
	 */
	private final String msg;


}
