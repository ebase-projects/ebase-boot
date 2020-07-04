package me.dwliu.framework.plugin.security.validatecode.enums;


import me.dwliu.framework.core.security.constant.SecurityConstants;

/**
 * 验证码类型枚举
 *
 * @author liudw
 * @date 2020/7/4 19:52
 **/
public enum ValidateCodeTypeEnum {

	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};


	/**
	 * 校验时从请求中获取的参数的名称
	 *
	 * @return
	 */
	public abstract String getParamNameOnValidate();


}
