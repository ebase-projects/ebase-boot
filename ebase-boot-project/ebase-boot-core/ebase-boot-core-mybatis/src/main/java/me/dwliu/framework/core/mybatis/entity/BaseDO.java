package me.dwliu.framework.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类(ID)，所有实体都需要继承
 *
 * @author liudw
 * @date 2019-06-22 10:44
 **/
@Data
public abstract class BaseDO implements Serializable {

	/**
	 * 主键id 主键交由子类实现
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
//     @TableId
	private Serializable id;
	//
	// /**
	//  * 租户编号
	//  */
	// @Schema(description = "租户编号")
	// private String tenantId;

	/**
	 * 创建部门
	 */
	// @JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "创建部门")
	@TableField(fill = FieldFill.INSERT)
	private Serializable createDept;
	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Serializable createBy;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Serializable updateBy;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 逻辑删除状态[0:未删除,1:删除]
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private Integer delFlag;

	/**
	 * 乐观锁
	 * <p>
	 * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
	 */
	@Version
	private Integer revision;


}
