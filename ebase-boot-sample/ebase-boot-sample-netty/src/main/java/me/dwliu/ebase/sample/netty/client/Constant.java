package me.dwliu.ebase.sample.netty.client;

/**
 * 常量
 *
 * @author liudw
 * @date 2021/6/1 17:40
 **/
public class Constant {

	/**
	 * 回车换行
	 */
	private static final String NEWLINE = "\r\n";
	/**
	 * 登陆
	 */
	public static final String LOGIN = "i sdyt,sdyt0116" + NEWLINE;
	/**
	 * 退出
	 */
	public static final String LOGINOUT = "o" + NEWLINE;
	/**
	 * 心跳检测
	 */
	public static final String HEARTBEAT = "k " + System.currentTimeMillis() + NEWLINE;


}
