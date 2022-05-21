package me.dwliu.ebase.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import me.dwliu.framework.core.mybatis.dto.BaseDTO;

/**
 * 角色管理
 *
 * @author eric
 * @since 1.0.0
 */
@Data
@Schema(description = "角色管理")
public class RoleDTO extends BaseDTO {

	private Long id;
	private String rolename;

}
