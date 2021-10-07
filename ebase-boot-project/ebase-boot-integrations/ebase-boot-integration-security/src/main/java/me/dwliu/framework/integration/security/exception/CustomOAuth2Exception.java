package me.dwliu.framework.integration.security.exception;


import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2Exception，解决返回异常与系统异常不一致的返回形式问题
 *
 * @author liudw
 * @date 2020/8/22 11:47
 **/
public class CustomOAuth2Exception extends OAuth2Exception {
	private String msg;

	public CustomOAuth2Exception(String msg) {
		super(msg);
		this.msg = msg;
	}

	public CustomOAuth2Exception(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
