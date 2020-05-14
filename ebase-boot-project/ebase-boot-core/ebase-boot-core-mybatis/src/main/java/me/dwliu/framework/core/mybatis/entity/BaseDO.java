package me.dwliu.framework.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类(ID)，所有实体都需要继承
 *
 * @author liudw
 * @date 2019-06-22 10:44
 **/
@Data
public abstract class BaseDO implements Serializable {

	/**
	 * 主键id
	 */
//	@TableId(value = "id", type = IdType.ID_WORKER)
	@TableId
	private Long id;

//    /**
//     * 租户编号
//     */
//    @ApiModelProperty(value = "租户编号")
//    private String tenantId;

//    /**
//     * 创建部门
//     */
	//@JsonSerialize(using = ToStringSerializer.class)
//    @ApiModelProperty(value = "创建部门")
//    private Long createDept;
	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUser;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private Date createDate;

	/**
	 * 更新人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUser;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;

	/**
	 * 状态[0:未删除,1:删除]
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private Integer isDeleted;

	/**
	 * 乐观锁
	 * <p>
	 * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
	 */
	@Version
	private Integer revision;


}
