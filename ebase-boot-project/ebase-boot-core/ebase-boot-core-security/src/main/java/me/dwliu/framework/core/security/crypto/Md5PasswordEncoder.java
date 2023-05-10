package me.dwliu.framework.core.security.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 自定义Md5
 *
 * @author liudw
 * @date 2020/7/6 16:39
 **/

public class Md5PasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return getMd5(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return getMd5(rawPassword.toString()).equals(encodedPassword);
	}


	private String getMd5(String input) {
		try {
			// Static getInstance method is called with hashing SHA
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method called
			// to calculate message digest of an input
			// and return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {

			return null;
		}
	}
}
