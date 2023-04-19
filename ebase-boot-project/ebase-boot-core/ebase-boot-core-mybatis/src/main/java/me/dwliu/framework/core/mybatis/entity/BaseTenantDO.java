package me.dwliu.framework.core.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 租户表默认实体
 *
 * @author liudw
 * @date 2019-06-22 10:44
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseTenantDO extends BaseDO {

	/**
	 * 租户编号
	 */
	private Serializable tenantId;

}
