package me.dwliu.framework.plugin.datascope.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.datascope.annotation.DataScopeFilter;
import me.dwliu.framework.core.datascope.constant.DataScopeConstant;
import me.dwliu.framework.core.datascope.enums.DataScopeViewEnum;
import me.dwliu.framework.core.datascope.model.DataScopeModel;
import me.dwliu.framework.core.datascope.model.RoleDataScopeModel;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.core.security.utils.SecurityUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据过滤
 *
 * @author liudw
 * @date 2020/10/27 17:27
 **/
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeFilterInterceptor extends AbstractSqlParserHandler implements Interceptor {

    private JdbcTemplate jdbcTemplate;

    private String dataScopeLevel;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // SQL解析
        this.sqlParser(metaObject);

        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }


        UserInfoDetails user = SecurityUtils.getUser();
        if (user == null) {
            return invocation.proceed();
        }

        //如果是超级管理员，则不进行数据过滤
        if (user.getSuperAdmin().intValue() == 1) {
            return invocation.proceed();
        }

        //根据条件生成sql 文件
        String sqlFilter = getSqlFilter(user, mappedStatement);
        log.debug("根据条件生成sql:{}", sqlFilter);

        // 不用数据过滤
        if (StringUtils.isBlank(sqlFilter)) {
            return invocation.proceed();
        }

        DataScopeModel scope = new DataScopeModel(sqlFilter);

        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        // Object paramObj = boundSql.getParameterObject();

        // 拼接新SQL
        originalSql = getSelect(originalSql, scope);
        log.debug("拼接新SQL:{}", originalSql);

        // 重写SQL
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        return invocation.proceed();
    }

    /**
     * 拼接新SQL
     *
     * @param originalSql 原始SQL
     * @param scope
     * @return
     */
    private String getSelect(String originalSql, DataScopeModel scope) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(originalSql);
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            Expression expression = plainSelect.getWhere();
            if (expression == null) {
                plainSelect.setWhere(new StringValue(scope.getSqlFilter()));
            } else {
                AndExpression andExpression = new AndExpression(expression, new StringValue(scope.getSqlFilter()));
                plainSelect.setWhere(andExpression);
            }

            return select.toString().replaceAll("'", "");
        } catch (JSQLParserException e) {
            return originalSql;
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }


    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(UserInfoDetails user, MappedStatement mappedStatement) throws Exception {
        StringBuilder sqlFilter = new StringBuilder();


        //组合角色数据
        String roleIds = user.getRoleId();
        Set<Long> roleIdSet = new HashSet<>();

        if (!StringUtils.isBlank(roleIds)) {
            String[] roleIdStrs = StringUtils.split(roleIds, ",");

            List<String> roleIdStrList = Arrays.asList(roleIdStrs);
            if (roleIdStrList != null && roleIdStrList.size() != 0) {
                roleIdStrList.forEach(p -> {
                    roleIdSet.add(Long.parseLong(p));
                });
            }

        }

        // 根据角色信息查询拥有的数据权限列表
        List<RoleDataScopeModel> roleDataScopeModels = getDataScopeByRoleId(roleIdSet);


        //查询DataScopeFilter注释
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(".")));
        String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length());
        for (Method method : classType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(DataScopeFilter.class) && mName.equals(method.getName())) {
                DataScopeFilter dataFilter = method.getAnnotation(DataScopeFilter.class);
                //获取表的别名
                String tableAlias = dataFilter.tableAlias();
                if (StringUtils.isNotBlank(tableAlias)) {
                    tableAlias += ".";
                }

                //设置优先级
                if (roleDataScopeModels == null || roleDataScopeModels.size() == 0) {
                    if (getDataScopeLevel().equalsIgnoreCase("ALL")) {
                        //默认全部
                        return "";
                    } else {
                        sqlFilter.append(" (");
                        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getUserId());
                        sqlFilter.append(")");
                        return sqlFilter.toString();
                    }
                }

                //根据type 分组
                Map<Integer, List<RoleDataScopeModel>> roleDataScopeModelsByMap = roleDataScopeModels
                        .stream()
                        .sorted(Comparator.comparing(RoleDataScopeModel::getScopeType))
                        .collect(Collectors.groupingBy(RoleDataScopeModel::getScopeType));


                if (roleDataScopeModelsByMap.containsKey(DataScopeViewEnum.ALL.getValue())) {
                    //全部
                    return "";
                }

                //部门集合
                Set<Long> customDeptId = new HashSet<>();

                //是否包含自己
                boolean isContainOwn = false;

                if (roleDataScopeModelsByMap.containsKey(DataScopeViewEnum.CUSTOM.getValue())) {
                    //自定义
                    List<RoleDataScopeModel> tmp = roleDataScopeModelsByMap.get(DataScopeViewEnum.CUSTOM.getValue());
                    RoleDataScopeModel roleDataScopeModel = tmp.get(0);
                    if (roleDataScopeModel != null) {
                        String deptIdStrs = roleDataScopeModel.getDeptIds();
                        if (StringUtils.isNotBlank(deptIdStrs)) {
                            String[] split = StringUtils.substring(deptIdStrs, 1, deptIdStrs.length() - 1).split(",");

                            List<String> deptIdStrList = Arrays.asList(split);

                            customDeptId = deptIdStrList.stream().map(x -> (Long.parseLong(StringUtils.trim(x)))).collect(Collectors.toSet());
                        }
                    }
                }

                if (roleDataScopeModelsByMap.containsKey(DataScopeViewEnum.OWN.getValue())) {
                    //自己
                    isContainOwn = true;
                }

                if (roleDataScopeModelsByMap.containsKey(DataScopeViewEnum.DEPT.getValue())) {
                    //部门
                    String deptIdStr = SecurityUtils.getUser().getDeptId();
                    if (!StringUtils.isBlank(deptIdStr)) {
                        long deptId = Long.parseLong(deptIdStr);
                        customDeptId.add(deptId);
                    }

                }

                if (roleDataScopeModelsByMap.containsKey(DataScopeViewEnum.DEPT_CHILD.getValue())) {
                    //部门及子部门
                    String deptIdStr = SecurityUtils.getUser().getDeptId();
                    if (!StringUtils.isBlank(deptIdStr)) {
                        long deptId = Long.parseLong(deptIdStr);
                        customDeptId.add(deptId);
                        List<Long> list = getSubDeptBydeptId(deptId);
                        customDeptId.addAll(list);
                    }
                }

                sqlFilter.append(" (");

                //部门ID列表
                if (CollUtil.isNotEmpty(customDeptId)) {
                    sqlFilter.append(tableAlias).append(dataFilter.deptId());

                    sqlFilter.append(" in(").append(StringUtils.join(customDeptId, ",")).append(")");
                }

                //查询本人数据
                if (CollUtil.isNotEmpty(customDeptId)) {
                    if (isContainOwn) {
                        sqlFilter.append(" or ");
                        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getUserId());

                    }
                } else {
                    if (isContainOwn) {
                        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getUserId());

                    }
                }

                sqlFilter.append(")");

                break;

            }
        }

        return sqlFilter.toString();
    }


    /**
     * 根据角色ID查询数据范围
     *
     * @param roleIds
     * @return
     */
    private List<RoleDataScopeModel> getDataScopeByRoleId(Set<Long> roleIds) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        String sql = DataScopeConstant.SQL_ROLE_DATA_SCOPE;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("roleIds", roleIds);
        List<RoleDataScopeModel> list = namedParameterJdbcTemplate.query(sql, parameterSource, (rs, index) -> {
            RoleDataScopeModel model = new RoleDataScopeModel();
            model.setRoleId(rs.getLong("role_id"));
            model.setScopeType(rs.getInt("scope_type"));
            model.setDeptIds(rs.getString("dept_ids"));
            return model;
        });
        return list;
    }


    /**
     * 根据deptId 查询子部门集合
     *
     * @param deptId
     * @return
     */
    private List<Long> getSubDeptBydeptId(Long deptId) {
        List<Map> list = jdbcTemplate.queryForList(DataScopeConstant.SQL_SYS_DEPT, new Object[]{deptId}, Map.class);
        List<Long> deptIds = new ArrayList<>();
        for (Map row : list) {
            deptIds.add((Long) row.get("id"));
        }
        return deptIds;
    }


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getDataScopeLevel() {
        return dataScopeLevel;
    }

    public void setDataScopeLevel(String dataScopeLevel) {
        this.dataScopeLevel = dataScopeLevel;
    }
}
