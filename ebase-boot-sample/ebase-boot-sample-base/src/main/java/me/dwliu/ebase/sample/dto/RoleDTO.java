package me.dwliu.ebase.sample.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色管理
 *
 * @author eric
 * @since 1.0.0
 */
@Data
@ApiModel(value = "角色管理")
public class RoleDTO implements Serializable {

	private Long id;
	private String rolename;

}
