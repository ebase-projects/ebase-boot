package me.dwliu.lab.core.oss.util;

import me.dwliu.lab.core.oss.exception.OssException;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片与base64互转工具类
 *
 * @author liudw
 * @date 2019-03-21 09:20
 **/
public class ImageBase64Util {
	/**
	 * 将网络图片进行Base64位编码
	 *
	 * @param imageUrl   图片的url路径，如http://.....xx.jpg
	 * @return
	 */
	public static String encodeImgageToBase64(URL imageUrl) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageUrl);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回Base64编码过的字节数组字符串
		return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}

	/**
	 * 将本地图片进行Base64位编码
	 *
	 * @param imageFile
	 * @return
	 */
	public static String encodeImgageToBase64(File imageFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageFile);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回Base64编码过的字节数组字符串
		return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}

	/**
	 * 将Base64位编码的图片进行解码，并保存到指定目录
	 *
	 * @param base64  base64编码的图片信息
	 * @return
	 */
	public static void decodeBase64ToImage(String base64, String path, String imgName) {
		try {
			FileOutputStream write = new FileOutputStream(new File(path + imgName));
			byte[] decoderBytes = Base64.getDecoder().decode(base64.getBytes());
			write.write(decoderBytes);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OssException("图片转码异常：" + e.getClass().getName() + ":" + e.getMessage());
		}
	}

	/**
	 * 将Base64位编码的图片进行解码
	 *
	 * @param base64Data eg:data:image/jpg;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBg………………
	 * @return
	 */
	public static byte[] decodeBase64ToImageByte(String base64Data) {
		String data = "";
		if (StringUtils.isBlank(base64Data)) {
			throw new OssException("上传图片数据为空");
		} else {
			String[] d = base64Data.split("base64,");
			if (d != null && d.length == 2) {
				data = d[1];
			} else {
				throw new OssException("数据不合法");
			}
		}
		//因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
		//byte[] bs = Base64Utils.decodeFromString(data);

		byte[] bytes = Base64.getDecoder().decode(data.getBytes());
		return bytes;

	}


	/**
	 * 获取Base64 图片后缀
	 *
	 * @param base64Data
	 * @return
	 */
	public static String getImageExtension(String base64Data) {
		String dataPrix = "";
		if (StringUtils.isBlank(base64Data)) {
			throw new OssException("上传图片数据为空");
		} else {
			String[] d = base64Data.split("base64,");
			if (d != null && d.length == 2) {
				dataPrix = d[0];
			} else {
				throw new OssException("数据不合法");
			}
		}
		String suffix = "";
		if ("data:image/jpg;".equalsIgnoreCase(dataPrix) || "data:image/jpeg;".equalsIgnoreCase(dataPrix)) {
			//data:image/jpeg;base64,base64编码的jpeg图片数据
			suffix = "jpg";
		} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {
			//data:image/x-icon;base64,base64编码的icon图片数据
			suffix = "ico";
		} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {
			//data:image/gif;base64,base64编码的gif图片数据
			suffix = "gif";
		} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {
			//data:image/png;base64,base64编码的png图片数据
			suffix = "png";
		} else {
			throw new OssException("上传图片格式不合法");
		}

		return suffix;
	}


	/**
	 * 获取base64图片的数据和后缀
	 *
	 * @param imgString
	 * @return {@code Map<String, Object>}  data base64 解密后byte,imgType 为图片后缀
	 */
	public static Map<String, Object> getImageDataAndSuffix(String imgString) {
		Map<String, Object> result = new HashMap<>(2);
		//允许的图片格式（可配置）
		String imgType = "jpg,png,jpeg,gif,ico";
		String formatStr = "data:image/{0};base64,";

		if (!StringUtils.isEmpty(imgType)) {
			String[] imgTypes = imgType.split(",");
			Pattern pattern;
			Matcher matcher;
			String regex;
			for (String v : imgTypes) {
				regex = MessageFormat.format(formatStr, v);
				pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(imgString);
				String data = "";
				//判断是否匹配
				if (matcher.lookingAt()) {
					data = matcher.replaceFirst("");
					result.put("data", Base64.getDecoder().decode(data));
					result.put("imgType", v);
				}

			}
		}
		return result;
	}


}
