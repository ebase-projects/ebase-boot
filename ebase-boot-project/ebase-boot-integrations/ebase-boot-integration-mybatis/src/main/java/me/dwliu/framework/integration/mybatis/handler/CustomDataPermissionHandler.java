//package me.dwliu.framework.integration.mybatis.handler;
//
//import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
//import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.HexValue;
//import net.sf.jsqlparser.expression.StringValue;
//import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
//import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
//import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
//import net.sf.jsqlparser.expression.operators.relational.InExpression;
//import net.sf.jsqlparser.expression.operators.relational.ItemsList;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class CustomDataPermissionHandler implements DataPermissionHandler {
//
//    /**
//     * @param where             原SQL Where 条件表达式
//     * @param mappedStatementId Mapper接口方法ID
//     * @return
//     */
//    @SneakyThrows
//    @Override
//    public Expression getSqlSegment(Expression where, String mappedStatementId) {
//		DataPermissionInterceptor
//        // 1. 获取权限过滤相关信息
//        DataFilterMetaData dataFilterMetaData = DataFilterThreadLocal.get();
//        try {
//            log.debug("开始进行权限过滤,dataFilterMetaData:{} , where: {},mappedStatementId: {}", dataFilterMetaData, where, mappedStatementId);
//            if (dataFilterMetaData == null) {
//                return where;
//            }
//            Expression expression = new HexValue(" 1 = 1 ");
//            if (where == null) {
//                where = expression;
//            }
//            switch (dataFilterMetaData.filterType) {
//                // 查看全部
//                case ALL:
//                    return where;
//                // 查看本人所在组织机构以及下属机构
//                case DEPT_SETS:
//                    // 创建IN 表达式
//                    // 创建IN范围的元素集合
//                    Set<Long> deptIds = dataFilterMetaData.getDeptIds();
//                    // 把集合转变为JSQLParser需要的元素列表
//                    ItemsList itemsList = new ExpressionList(deptIds.stream().map(LongValue::new).collect(Collectors.toList()));
//                    InExpression inExpression = new InExpression(new Column("create_dept_id"), itemsList);
//                    return new AndExpression(where, inExpression);
//                // 查看当前部门的数据
//                case DEPT:
//                    //  = 表达式
//                    // dept_id = deptId
//                    EqualsTo equalsTo = new EqualsTo();
//                    equalsTo.setLeftExpression(new Column("create_dept_id"));
//                    equalsTo.setRightExpression(new LongValue(dataFilterMetaData.getDeptId()));
//                    // 创建 AND 表达式 拼接Where 和 = 表达式
//                    // WHERE xxx AND dept_id = 3
//                    return new AndExpression(where, equalsTo);
//                // 查看自己的数据
//                case SELF:
//                    // create_by = userId
//                    EqualsTo selfEqualsTo = new EqualsTo();
//                    selfEqualsTo.setLeftExpression(new Column("create_by"));
//                    selfEqualsTo.setRightExpression(new LongValue(dataFilterMetaData.getUserId()));
//                    return new AndExpression(where, selfEqualsTo);
//                case DIY:
//                    return new AndExpression(where, new StringValue(dataFilterMetaData.getSql()));
//                default:
//                    break;
//            }
//        } catch (Exception e) {
//            log.error("LakerDataPermissionHandler.err", e);
//        } finally {
//            DataFilterThreadLocal.clear();
//        }
//        return where;
//    }
//}
