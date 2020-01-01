package me.dwliu.framework.core.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.dwliu.framework.common.enums.YesOrNoEnum;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 公共字段，自动填充值
 *
 * @author liudw
 * @date 2019-07-02 22:20
 **/
//@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
	private final static String CREATE_DATE = "createDate";
	private final static String UPDATE_DATE = "updateDate";

	private final static String CREATE_USER = "createUser";
	private final static String UPDATE_USER = "updateUser";
	private final static String DEPT_ID = "deptId";
	private final static String IS_DELETED = "isDeleted";

	@Override
	public void insertFill(MetaObject metaObject) {
		//UserDetail user = SecurityUser.getUser();

		//创建者
		//strictInsertFill(metaObject, CREATE_USER, Long.class, user.getId());
		//创建者所属部门
		//strictInsertFill(metaObject, DEPT_ID, Long.class, user.getDeptId());
		//更新者
		//strictInsertFill(metaObject, UPDATE_USER, Long.class, user.getId());

		Date date = new Date();
		//创建时间
		strictInsertFill(metaObject, CREATE_DATE, Date.class, date);
//		setInsertFieldValByName(CREATE_DATE, date, metaObject);
		//更新时间
		strictInsertFill(metaObject, UPDATE_DATE, Date.class, date);
//		setInsertFieldValByName(UPDATE_DATE, date, metaObject);
		//是否删除 默认为0 不删除
		strictInsertFill(metaObject, IS_DELETED, Integer.class, YesOrNoEnum.NO.getValue());
//		setInsertFieldValByName(IS_DELETED, YesOrNoEnum.NO.getValue(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		//更新者
		//setUpdateFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
		//strictInsertFill(metaObject, UPDATE_USER, Long.class, SecurityUser.getUserId());
		//更新时间
//		setUpdateFieldValByName(UPDATE_DATE, new Date(), metaObject);
		strictUpdateFill(metaObject, UPDATE_DATE, Date.class, new Date());
	}
}
