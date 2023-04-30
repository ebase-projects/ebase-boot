package me.dwliu.framework.integration.security.validatecode.image;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.dwliu.framework.integration.security.validatecode.DefaultValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码实体
 *
 * @author liudw
 * @date 2019-04-23 14:16
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ImageCode extends DefaultValidateCode {

	/**
	 * 验证码图片
	 */
	private BufferedImage image;

	/**
	 * @param image      验证码图片
	 * @param code       验证码
	 * @param expireTime 过期时间
	 */
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = image;
	}

	/**
	 * @param image    验证码图片
	 * @param code     验证码
	 * @param expireIn 过期秒数
	 */
	public ImageCode(BufferedImage image, String code, int expireIn) {
		super(code, expireIn);
		this.image = image;
	}
}
