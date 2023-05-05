package me.dwliu.framework.integration.security.validatecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * servlet工具类
 *
 * @author liudw
 * @date 2023/5/5 20:12
 **/
public class ServletRequestUtils {
	/**
	 * 从body 获取json
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static Map<String, String> getBodyByJson(HttpServletRequest request) throws IOException {

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> params = objectMapper.readValue(responseStrBuilder.toString(), Map.class);

		return params;
	}

}
