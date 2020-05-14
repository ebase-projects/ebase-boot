package me.dwliu.framework.plugin.datascope.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.mongodb.intercept.QueryInterceptor;
import org.apache.ibatis.plugin.Invocation;


/**
 * mybatis 数据权限拦截器
 *
 * @author L.cm, Chill
 */
@Slf4j
@RequiredArgsConstructor
public class DataScopeInterceptor implements QueryInterceptor {
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return null;
	}

//	private ConcurrentMap<String, DataFilter> dataAuthMap = new ConcurrentHashMap<>(8);
//
//	private final DataScopeHandler dataScopeHandler;
//	private final DataScopeProperties dataScopeProperties;
//
//	@Override
//	public Object intercept(Invocation invocation) throws Throwable {
//		//未取到用户则放行
//		BladeUser bladeUser = AuthUtil.getUser();
//		if (bladeUser == null) {
//			return invocation.proceed();
//		}
//
//		StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
//		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//
//		//非SELECT操作放行
//		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
//		if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()
//			|| StatementType.CALLABLE == mappedStatement.getStatementType()) {
//			return invocation.proceed();
//		}
//
//		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
//		String originalSql = boundSql.getSql();
//
//		//查找注解中包含DataAuth类型的参数
//		DataFilter dataAuth = findDataAuthAnnotation(mappedStatement);
//
//		//注解为空并且数据权限方法名未匹配到,则放行
//		String mapperId = mappedStatement.getId();
//		String className = mapperId.substring(0, mapperId.lastIndexOf(StringPool.DOT));
//		String mapperName = ClassUtil.getShortName(className);
//		String methodName = mapperId.substring(mapperId.lastIndexOf(StringPool.DOT) + 1);
//		boolean mapperSkip = dataScopeProperties.getMapperKey().stream().noneMatch(methodName::contains)
//			|| dataScopeProperties.getMapperExclude().stream().anyMatch(mapperName::contains);
//		if (dataAuth == null && mapperSkip) {
//			return invocation.proceed();
//		}
//
//		//创建数据权限模型
//		DataScopeModel dataScope = new DataScopeModel();
//
//		//若注解不为空,则配置注解项
//		if (dataAuth != null) {
//			dataScope.setResourceCode(dataAuth.code());
//			dataScope.setScopeColumn(dataAuth.column());
//			dataScope.setScopeType(dataAuth.type().getType());
//			dataScope.setScopeField(dataAuth.field());
//			dataScope.setScopeValue(dataAuth.value());
//		}
//
//		//获取数据权限规则对应的筛选Sql
//		String sqlCondition = dataScopeHandler.sqlCondition(invocation, mapperId, dataScope, bladeUser, originalSql);
//		if (StringUtils.isBlank(sqlCondition)) {
//			return invocation.proceed();
//		} else {
//			metaObject.setValue("delegate.boundSql.sql", sqlCondition);
//			return invocation.proceed();
//		}
//	}
//
//	/**
//	 * 获取数据权限注解信息
//	 *
//	 * @param mappedStatement mappedStatement
//	 * @return DataAuth
//	 */
//	private DataFilter findDataAuthAnnotation(MappedStatement mappedStatement) {
//		String id = mappedStatement.getId();
//		return dataAuthMap.computeIfAbsent(id, (key) -> {
//			String className = key.substring(0, key.lastIndexOf(StringPool.DOT));
//			String mapperBean = StringUtil.firstCharToLower(ClassUtil.getShortName(className));
//			Object mapper = SpringUtil.getBean(mapperBean);
//			String methodName = key.substring(key.lastIndexOf(StringPool.DOT) + 1);
//			Class<?>[] interfaces = ClassUtil.getAllInterfaces(mapper);
//			for (Class<?> mapperInterface : interfaces) {
//				for (Method method : mapperInterface.getDeclaredMethods()) {
//					if (methodName.equals(method.getName()) && method.isAnnotationPresent(DataFilter.class)) {
//						return method.getAnnotation(DataFilter.class);
//					}
//				}
//			}
//			return null;
//		});
//	}

}
