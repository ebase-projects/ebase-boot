package me.dwliu.framework.integration.security.validatecode.image;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.dwliu.framework.integration.security.validatecode.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码实体
 *
 * @author liudw
 * @date 2019-04-23 14:16
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCode extends ValidateCode {

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
