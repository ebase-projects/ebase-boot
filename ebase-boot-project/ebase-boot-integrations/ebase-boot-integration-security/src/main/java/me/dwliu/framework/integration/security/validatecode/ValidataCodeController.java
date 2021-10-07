package me.dwliu.framework.integration.security.validatecode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码Controller
 *
 * @author liudw
 * @date 2020/7/4 21:38
 **/
@RestController
@Slf4j
public class ValidataCodeController {
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;


	@GetMapping("/code/{type}")
	public void createCode(HttpServletRequest request,
	                       HttpServletResponse response,
	                       @PathVariable(value = "type") String typeCode) {
		ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.getValidateCodeProcessor(typeCode);

		validateCodeProcessor.create(new ServletWebRequest(request, response));


	}

}
