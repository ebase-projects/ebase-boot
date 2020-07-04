package me.dwliu.framework.plugin.security.validatecode.image;

import me.dwliu.framework.plugin.security.validatecode.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片验证码处理器
 *
 * @author liudw
 * @date 2019-04-23 16:46
 **/
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
	/**
	 * 发送验证码
	 *
	 * @param request   spring 封装的request
	 * @param imageCode 验证码
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) {
		HttpServletResponse response = request.getResponse();
		try {
			assert response != null;
			response.setHeader("Cache-Control", "no-store, no-cache");
			response.setContentType("image/jpeg");
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
