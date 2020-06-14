package me.dwliu.ebase.sample.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.dwliu.framework.core.mybatis.entity.BaseDO;

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
	@TableId
	private Long id;

	private String rolename;

}
