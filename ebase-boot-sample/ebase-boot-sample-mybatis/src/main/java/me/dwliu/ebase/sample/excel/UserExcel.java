package me.dwliu.ebase.sample.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 用户管理
 *
 * @author eric
 * @since 1.0.0
 */
@Data
public class UserExcel implements Serializable {

	private Long id;

	@Excel(name = "用户名")
	private String username;

	@Excel(name = "密码")
	private String password;

	@Excel(name = "姓名")
	private String realName;

	private String headUrl;

	@ApiModelProperty(value = "性别   0：男   1：女    2：保密", required = true)
	private Integer gender;

	@Excel(name = "邮箱")
	private String email;

	@Excel(name = "手机号")
	private String mobile;


	@ApiModelProperty(value = "状态  0：停用    1：正常", required = true)
	@Range(min = 0, max = 1, message = "状态必须在合理的范围内:{min}-{max}")
	private Integer status;


}
