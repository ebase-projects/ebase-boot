package me.dwliu.framework.autoconfigure.security.code;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图形验证码配置属性
 *
 * @author liudw
 * @date 2019-04-23 11:20
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageCodeProperties extends SmsCodeProperties {
	/**
	 * 初始化验证码长度为6
	 */
	public ImageCodeProperties() {
		setLength(4);
	}

	/**
	 * 验证码宽度
	 */
	private int width = 90;
	/**
	 * 验证码高度
	 */
	private int height = 20;

}
