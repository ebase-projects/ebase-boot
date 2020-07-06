package me.dwliu.framework.core.security.crypto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 仅仅是测试
 *
 * @author liudw
 * @date 2020/7/6 17:25
 **/
@Slf4j
public class PasswordEncoderTest {
	public static void main(String[] args) {

		String originStr = "123qwe";
		PasswordEncoder passwordEncoder = CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();

		String bcryptEncode = passwordEncoder.encode(originStr);
		log.info("bcrypt encode: {}", bcryptEncode);
		log.info("bcrypt matches: {}", passwordEncoder.matches(originStr, bcryptEncode));


		// 自定义Md5
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String md5Encode = "{md5}" + md5PasswordEncoder.encode(originStr);
		log.info("md5 encode: {}", md5Encode);
		log.info("md5 matches: {}", passwordEncoder.matches(originStr, md5Encode));

		// 没有密码
		PasswordEncoder noOpPasswordInstance = NoOpPasswordEncoder.getInstance();
		String noopEncode = "{noop}" + noOpPasswordInstance.encode(originStr);
		log.info("noop encode: {}", noopEncode);
		log.info("noop matches: {}", passwordEncoder.matches(originStr, noopEncode));


	}
}
