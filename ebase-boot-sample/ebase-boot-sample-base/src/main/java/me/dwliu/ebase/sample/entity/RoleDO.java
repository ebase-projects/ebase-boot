package me.dwliu.ebase.sample.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.dwliu.framework.core.base.entity.BaseDO;

import java.io.Serializable;

/**
 * 角色管理
 *
 * @author eric
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "角色管理")
public class RoleDO extends BaseDO {

	private String rolename;

}
