/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.dwliu.framework.core.security.crypto;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for creating {@link PasswordEncoder} instances
 *
 * <p>{@link org.springframework.security.crypto.factory.PasswordEncoderFactories}</p>
 *
 * @author Rob Winch liudw
 * @since 5.0
 */
public class CustomPasswordEncoderFactories {

	/**
	 * 创建默认的PasswordEncoder（bcrypt）
	 *
	 * @return
	 */
	public static PasswordEncoder createDelegatingPasswordEncoder() {
//		String encodingId = "bcrypt";
//		Map<String, PasswordEncoder> encoders = new HashMap<>();
//		encoders.put(encodingId, new BCryptPasswordEncoder());
//		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
//		encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
//		// 不加 salt,为了解决历史遗漏项目仅使用md5 场景
//		encoders.put("md5", new Md5PasswordEncoder());
//		encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
//		// encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
//		// 仅仅是copy
//		encoders.put("noop", NoOpPasswordEncoder.getInstance());
//		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//		encoders.put("scrypt", new SCryptPasswordEncoder());
//		encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
//		encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
//		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
//		encoders.put("argon2", new Argon2PasswordEncoder());
//
//		return new DelegatingPasswordEncoder(encodingId, encoders);


		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = getStringPasswordEncoderMap(encodingId);
		return new DelegatingPasswordEncoder(encodingId, encoders);


	}


	/**
	 * 创建指定的PasswordEncoder
	 *
	 * @param encodingId
	 * @return
	 */
	public static PasswordEncoder createDelegatingPasswordEncoder(String encodingId) {
		Map<String, PasswordEncoder> encoders = getStringPasswordEncoderMap(encodingId);
		Assert.isTrue(encoders.containsKey(encodingId), encodingId + " is not found in idToPasswordEncoder");
		return new DelegatingPasswordEncoder(encodingId, encoders);
	}

	/**
	 * 加密集合
	 * <p>
	 * ldap,MD4,MD5,pbkdf2,scrypt,SHA-1,SHA-256,argon2,md5,noop,SM3,
	 * </p>
	 *
	 * @param encodingId 编码ID （ldap,MD4,MD5,pbkdf2,scrypt,SHA-1,SHA-256,argon2,md5,noop,SM3,）
	 * @return
	 */
	private static Map<String, PasswordEncoder> getStringPasswordEncoderMap(String encodingId) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
		encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
		encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
		encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
		encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
		encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
		encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
		encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		//自定义PasswordEncoder
		// 不加 salt,为了解决历史遗漏项目仅使用md5 场景
		encoders.put("md5", new Md5PasswordEncoder());
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("SM3", new SM3PasswordEncoder());
		return encoders;
	}

	private CustomPasswordEncoderFactories() {
	}

}
