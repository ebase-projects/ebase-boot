package me.dwliu.lab.core.tool.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * SOAP 工具类。
 * <pre>
 * 描述：TODO
 * </pre>
 */
public class SoapUtil {

	protected static Logger logger = LoggerFactory.getLogger(SoapUtil.class);

	/**
	 * 获取请求的数据
	 *
	 * @param url     请求的URl
	 * @param soapXml soap 包的Xml
	 * @return map的key :
	 * <p>
	 * success : 是否成功，true 成功，false 失败
	 * <p>
	 * code : 状态码，http 请求状态码200 表示成功，500 表示服务器异常 -100表示服务器未启动
	 * <p>
	 * response ： 请求返回的soap包
	 * <p>
	 * result 错误正确返回的结果
	 */
	public static Map<String, Object> getResponse(String url, String soapXml) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		int code = -100;// 状态码
		boolean success = false;
		String result = "";

		HttpURLConnection conn = null;
		OutputStreamWriter os = null;
		InputStreamReader isr = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL servletURL = new URL(url);
			conn = (HttpURLConnection) servletURL.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");// 解决乱码问题
			// Send data
			os = new OutputStreamWriter(conn.getOutputStream());
			os.write(soapXml);
			os.flush();
			code = conn.getResponseCode();
			if (code == HttpStatus.OK.value()) {// 200 : OK（成功） 一切正常
				is = conn.getInputStream();
				success = true;
			} else {
				is = conn.getErrorStream();
			}
			// Get the response
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			logger.error("请求出错了.", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			result = sw.toString();
		} finally {
			// 关闭流
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("关闭流出错.", e);
			}
		}
		map.put("success", success);
		map.put("code", code);
		map.put("response", sb.toString());
		map.put("result", result);
		return map;
	}
}
