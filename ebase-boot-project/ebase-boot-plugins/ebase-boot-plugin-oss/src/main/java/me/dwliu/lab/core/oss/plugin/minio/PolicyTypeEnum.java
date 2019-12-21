package me.dwliu.lab.core.oss.plugin.minio;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * minio策略配置
 *
 * @author SCMOX
 */
@Getter
@AllArgsConstructor
public enum PolicyTypeEnum {

	/**
	 * 只读
	 */
	READ("read", "只读"),

	/**
	 * 只写
	 */
	WRITE("write", "只写"),

	/**
	 * 读写
	 */
	READ_WRITE("read_write", "读写");

	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String policy;

}
