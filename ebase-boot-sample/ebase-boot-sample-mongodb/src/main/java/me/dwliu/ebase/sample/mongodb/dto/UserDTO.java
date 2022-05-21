package me.dwliu.ebase.sample.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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

/**
 * 用户管理
 *
 * @author eric
 * @since 1.0.0
 */
@Data
@Schema(description = "用户管理")
public class UserDTO implements Serializable {

	@Schema(description = "id")
	@Null(message = "主键必须为空", groups = CreateGroup.class)
	@NotNull(message = "主键不能为空", groups = UpdateGroup.class)
	private String id;

	@Schema(description = "用户名", required = true)
	@NotBlank(message = "用户名不能为空")
	private String username;

	@Schema(description = "密码")
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "密码不能为空", groups = CreateGroup.class)
	private String password;

	@Schema(description = "姓名", required = true)
	@NotBlank(message = "姓名不能为空")
	private String realName;

	@Schema(description = "头像")
	private String headUrl;

	@Schema(description = "性别   0：男   1：女    2：保密", required = true)
	//@Range(min = 0, max = 2, message = "性别必须在合理的范围内:{min}-{max}")
	private Integer gender;

	@Schema(description = "邮箱", required = true)
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式不正确")
	private String email;

	@Schema(description = "手机号", required = true)
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@Schema(description = "部门ID", required = true)
	@NotNull(message = "部门不能为空")
	private Long deptId;

	@Schema(description = "状态  0：停用    1：正常", required = true)
	@Range(min = 0, max = 1, message = "状态必须在合理的范围内:{min}-{max}")
	private Integer status;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createTime;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "更新时间")
	//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date updateTime;

	@Schema(description = "超级管理员   0：否   1：是")
	@Range(min = 0, max = 1, message = "超级管理员必须在合理的范围内:{min}-{max}")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer superAdmin;


}
