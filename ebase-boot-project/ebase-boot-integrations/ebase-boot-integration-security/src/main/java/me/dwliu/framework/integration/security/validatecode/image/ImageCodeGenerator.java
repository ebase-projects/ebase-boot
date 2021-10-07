package me.dwliu.framework.integration.security.validatecode.image;

import me.dwliu.framework.integration.security.validatecode.ValidateCode;
import me.dwliu.framework.integration.security.validatecode.ValidateCodeGenerator;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图形验证码生成器
 *
 * @author liudw
 * @date 2019-04-23 14:22
 **/
public class ImageCodeGenerator implements ValidateCodeGenerator {

	private final int imageWidth;
	private final int imageHeight;
	private final int imageLength;
	private final int imageExpireIn;

	public ImageCodeGenerator(int imageWidth, int imageHeight, int imageLength, int imageExpireIn) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.imageLength = imageLength;
		this.imageExpireIn = imageExpireIn;
	}

	/**
	 * 生成验证码
	 *
	 * @param request spring 封装的request
	 * @return 验证码封装类
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		//验证码图片宽度
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", imageWidth);

		//验证码图片高度
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", imageHeight);


		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < imageLength; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}

		g.dispose();

		return new ImageCode(image, sRand, imageExpireIn);
	}

	/**
	 * 生成随机背景条纹
	 *
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}


}
