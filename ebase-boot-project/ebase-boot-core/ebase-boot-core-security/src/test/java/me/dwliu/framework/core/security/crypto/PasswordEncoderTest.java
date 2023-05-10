package me.dwliu.framework.core.security.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 仅仅是测试
 *
 * @author liudw
 * @date 2020/7/6 17:25
 **/
public class PasswordEncoderTest {
	public static void main(String[] args) {

		String originStr = "123qwe";
		PasswordEncoder passwordEncoder = CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();

		String bcryptEncode = passwordEncoder.encode(originStr);
		System.out.println(bcryptEncode);
		System.out.println(passwordEncoder.matches(originStr, bcryptEncode));


		// 自定义Md5
		PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String md5Encode = "{md5}" + md5PasswordEncoder.encode(originStr);
		System.out.println(md5Encode);
		System.out.println(passwordEncoder.matches(originStr, md5Encode));

		// 没有密码
		PasswordEncoder noOpPasswordInstance = NoOpPasswordEncoder.getInstance();
		String noopEncode = "{noop}" + noOpPasswordInstance.encode(originStr);
		System.out.println(noopEncode);
		System.out.println(passwordEncoder.matches(originStr, noopEncode));

		// 自定义sm3
		PasswordEncoder sm3 = CustomPasswordEncoderFactories.createDelegatingPasswordEncoder("SM3");
		String sm3Encode = sm3.encode(originStr);
		System.out.println(sm3Encode);
		System.out.println(passwordEncoder.matches(originStr, sm3Encode));

		PasswordEncoder sm3_ = new SM3PasswordEncoder();
		String sm3_Encode = "{SM3}"+sm3_.encode(originStr);
		System.out.println(sm3_Encode);
		System.out.println(passwordEncoder.matches(originStr, sm3_Encode));


	}
}
