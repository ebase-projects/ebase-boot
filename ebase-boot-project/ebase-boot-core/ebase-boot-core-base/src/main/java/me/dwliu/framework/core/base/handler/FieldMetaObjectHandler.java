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
		//setInsertFieldValByName(CREATOR, user.getId(), metaObject);
		//创建者所属部门
		//setInsertFieldValByName(DEPT_ID, user.getDeptId(), metaObject);
		//更新者
		//setInsertFieldValByName(UPDATER, user.getId(), metaObject);

		Date date = new Date();
		//创建时间
		setInsertFieldValByName(CREATE_DATE, date, metaObject);
		//更新时间
		setInsertFieldValByName(UPDATE_DATE, date, metaObject);
		//是否删除 默认为0 不删除
		setInsertFieldValByName(IS_DELETED, YesOrNoEnum.NO.getValue(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		//更新者
		//setUpdateFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
		//更新时间
		setUpdateFieldValByName(UPDATE_DATE, new Date(), metaObject);
	}
}
