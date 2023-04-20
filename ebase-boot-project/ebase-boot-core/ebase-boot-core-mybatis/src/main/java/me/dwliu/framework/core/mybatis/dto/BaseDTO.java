package me.dwliu.framework.core.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import me.dwliu.framework.common.validator.group.CreateGroup;
import me.dwliu.framework.common.validator.group.UpdateGroup;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liudw
 * @date 2019-08-26 15:15
 **/
@Data
public abstract class BaseDTO implements Serializable {

	/**
	 * 主键id
	 */
//	@Schema(description = "主键id")
//	@NotNull(message = "主键不能为空", groups = UpdateGroup.class)
//	@Null(message = "主键必须为空", groups = CreateGroup.class)
//	private Serializable id;

	// /**
	//  * 状态[1:正常]
	//  */
	// @Schema(description = "业务状态")
	// private Integer status;
	//
	/**
	 * 创建部门
	 */
	// @JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "创建部门")
	private Long createDept;

	@Schema(description = "创建人", hidden = true)
	private Long createBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "创建时间", hidden = true)
	private LocalDateTime createTime;

	@Schema(description = "更新人", hidden = true)
	private Long updateBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "更新时间", hidden = true)
	private LocalDateTime updateTime;

	@Schema(description = "是否已删除", hidden = true)
	private Integer delFlag;

	@Schema(description = "乐观锁", hidden = true)
	private Integer revision;
}
