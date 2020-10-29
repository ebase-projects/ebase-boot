package me.dwliu.framework.core.datascope.model;

import lombok.Data;

/**
 * 角色数据权限
 *
 * @author liudw
 * @date 2020/10/27 14:41
 **/
@Data
public class SysRoleDataScopeDTO {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 部门ID
     */
    private Long deptId;

}
