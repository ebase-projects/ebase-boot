package me.dwliu.framework.core.mybatis.datascope.model;

import lombok.Data;

/**
 * 角色数据权限
 *
 * @author liudw
 * @date 2020/10/27 14:41
 **/
@Data
public class RoleDataScopeModel {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 范围类型
     */
    private Integer scopeType;
    /**
     * 部门ID
     */
    private String deptIds;

}
