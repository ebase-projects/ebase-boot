package me.dwliu.framework.plugin.datascope.aspect;

import cn.hutool.core.collection.CollUtil;
import me.dwliu.framework.common.code.SystemResultCode;
import me.dwliu.framework.core.datascope.annotation.DataScopeFilter;
import me.dwliu.framework.core.datascope.constant.DataScopeConstant;
import me.dwliu.framework.core.datascope.exception.DataScopeException;
import me.dwliu.framework.core.datascope.model.DataScopeModel;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.core.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 数据过滤，切面处理类
 *
 * @author liudw
 * @date 2020/10/28 10:42
 **/
@Aspect
public class DataScopeFilterAspect {

    @Pointcut("@annotation(me.dwliu.framework.core.datascope.annotation.DataScopeFilter)")
    public void dataScopeFilterCut() {
    }

    @Before("dataScopeFilterCut()")
    public void dataScopeFilter(JoinPoint point) {
        Object params = point.getArgs()[0];
        if (params != null && params instanceof Map) {
            //UserInfoDetails user = SecurityUtils.getUser();

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("permission"));

            UserInfoDetails user = new UserInfoDetails("1318427505782218753", "", "", "1", "", "", "", "", "11", "111", 0, true, true, true, true, authorities);


            //如果是超级管理员，则不进行数据过滤
            if (user.getSuperAdmin().intValue() == 1) {
                return;
            }

            try {
                //否则进行数据过滤
                Map map = (Map) params;
                String sqlFilter = getSqlFilter(user, point);
                map.put(DataScopeConstant.SQL_FILTER, new DataScopeModel(sqlFilter));
            } catch (Exception e) {

            }
            return;
        }

        throw new DataScopeException(SystemResultCode.FAILURE);
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(UserInfoDetails user, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataScopeFilter dataFilter = method.getAnnotation(DataScopeFilter.class);

        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if (StringUtils.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        //TODO 部门ID列表
        //List<Long> deptIdList = user.getDeptIdList();
        List<Long> deptIdList = new ArrayList<>();
        if (CollUtil.isNotEmpty(deptIdList)) {
            sqlFilter.append(tableAlias).append(dataFilter.deptId());

            sqlFilter.append(" in(").append(StringUtils.join(deptIdList, ",")).append(")");
        }

        //查询本人数据
        if (CollUtil.isNotEmpty(deptIdList)) {
            sqlFilter.append(" or ");
        }
        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getUserId());

        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}
