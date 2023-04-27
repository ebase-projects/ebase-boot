package me.dwliu.framework.core.mybatis.datascope.model;

import java.io.Serializable;

/**
 * 数据权限实体类
 *
 * @author liudw
 * @date 2020-01-01 22:42
 **/
public class DataScopeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sqlFilter;

    public DataScopeModel(String sqlFilter) {
        this.sqlFilter = sqlFilter;
    }

    public String getSqlFilter() {
        return sqlFilter;
    }

    public void setSqlFilter(String sqlFilter) {
        this.sqlFilter = sqlFilter;
    }

    @Override
    public String toString() {
        return this.sqlFilter;
    }

}
