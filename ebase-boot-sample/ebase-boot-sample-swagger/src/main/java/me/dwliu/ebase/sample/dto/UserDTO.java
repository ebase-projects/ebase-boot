package me.dwliu.ebase.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.dwliu.framework.common.validator.group.CreateGroup;
import me.dwliu.framework.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 *
 * @author eric
 * @since 1.0.0
 */
@Data
@ApiModel(value = "用户管理")
public class UserDTO implements Serializable {

	@ApiModelProperty(value = "id")
	@Null(message = "主键必须为空", groups = CreateGroup.class)
	@NotNull(message = "主键不能为空", groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank(message = "用户名不能为空")
	private String username;

	@ApiModelProperty(value = "密码")
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "密码不能为空", groups = CreateGroup.class)
	private String password;

	@ApiModelProperty(value = "姓名", required = true)
	@NotBlank(message = "姓名不能为空")
	private String realName;

	@ApiModelProperty(value = "头像")
	private String headUrl;

	@ApiModelProperty(value = "性别   0：男   1：女    2：保密", required = true)
	@Range(min = 0, max = 2, message = "性别必须在合理的范围内:{min}-{max}")
	private Integer gender;

	@ApiModelProperty(value = "邮箱", required = true)
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式不正确")
	private String email;

	@ApiModelProperty(value = "手机号", required = true)
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@ApiModelProperty(value = "部门ID", required = true)
	@NotNull(message = "部门不能为空")
	private Long deptId;

	@ApiModelProperty(value = "状态  0：停用    1：正常", required = true)
	@Range(min = 0, max = 1, message = "状态必须在合理的范围内:{min}-{max}")
	private Integer status;

	@ApiModelProperty(value = "创建人")
	private String createBy;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createTime;

	@ApiModelProperty(value = "修改人")
	private String updateBy;

	@ApiModelProperty(value = "更新时间")
	//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date updateTime;

	@ApiModelProperty(value = "超级管理员   0：否   1：是")
	@Range(min = 0, max = 1, message = "超级管理员必须在合理的范围内:{min}-{max}")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer superAdmin;

	@ApiModelProperty(value = "是否删除 0正常 1删除")
	private Integer delFlag;

	@ApiModelProperty(value = "乐观锁")
	private Integer revision;

	@ApiModelProperty(value = "角色ID列表")
	private List<Long> roleIdList;

	@ApiModelProperty(value = "部门名称")
	private String deptName;


}
