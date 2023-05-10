package me.dwliu.framework.core.security.crypto;

import cn.hutool.crypto.SmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SM3国密加密
 *
 * @author liudw
 * @date 2023/5/10 12:29
 **/
@Slf4j
public class SM3PasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return SmUtil.sm3(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (rawPassword == null) {
			throw new IllegalArgumentException("rawPassword cannot be null");
		}
		if (encodedPassword == null || encodedPassword.length() == 0) {
			log.warn("Empty encoded password");
			return false;
		}
		String rawPasswordEncoded = this.encode(rawPassword.toString());
		return rawPasswordEncoded.equals(encodedPassword);
	}
}
