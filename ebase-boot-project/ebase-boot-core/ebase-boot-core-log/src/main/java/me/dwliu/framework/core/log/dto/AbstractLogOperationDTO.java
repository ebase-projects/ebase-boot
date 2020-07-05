package me.dwliu.framework.core.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志抽象类
 *
 * @author liudw
 * @date 2020/7/5 11:43
 **/
@Data
public abstract class AbstractLogOperationDTO implements Serializable {
	/**
	 * 用户操作类型
	 */
	private Integer type;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
