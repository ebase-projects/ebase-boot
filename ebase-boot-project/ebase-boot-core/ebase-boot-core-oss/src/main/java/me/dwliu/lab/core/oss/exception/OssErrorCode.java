package me.dwliu.lab.core.oss.exception;

/**
 * 错误编码，由6位数字组成，前3位为模块编码，后3位为业务编码
 * <p>
 * 如：100001（100代表系统模块，001代表业务代码）
 * </p>
 *
 * @author liudw
 * @date 2019-03-15 11:10
 **/
public interface OssErrorCode {
	int INTERNAL_SERVER_ERROR = 500;
	int UNAUTHORIZED = 401;
	int FORBIDDEN = 403;

	/**
	 * 上传文件异常
	 */
	int OSS_UPLOAD_FILE_ERROR = 500001;
	/**
	 * 文件不存在或者不是文件
	 */
	int OSS_UPLOAD_FILE_NOTEXISTS_OR_ISNOTFILE_ERROR = 500002;
	/**
	 * 七牛云异常
	 */
	int OSS_UPLOAD_FILE_QINIU_ERROR = 500003;

}
