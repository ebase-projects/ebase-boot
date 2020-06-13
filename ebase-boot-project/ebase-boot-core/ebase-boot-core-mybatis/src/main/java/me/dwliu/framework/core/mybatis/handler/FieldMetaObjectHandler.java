package me.dwliu.framework.core.mybatis.handler;

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
	private final static String CREATE_TIME = "createTime";
	private final static String UPDATE_TIME = "updateTime";

	private final static String CREATE_BY = "createBy";
	private final static String UPDATE_BY = "updateBy";
	private final static String CREATE_DEPT = "createDept";
	private final static String DEL_FLAG = "del_flag";

	@Override
	public void insertFill(MetaObject metaObject) {
		//UserDetail user = SecurityUser.getUser();

		//创建者
		//strictInsertFill(metaObject, CREATE_USER, Long.class, user.getId());
		//创建者所属部门
		//strictInsertFill(metaObject, DEPT_ID, Long.class, user.getDeptId());
		//更新者
		//strictInsertFill(metaObject, UPDATE_BY, Long.class, user.getId());

		Date date = new Date();
		//创建时间
		strictInsertFill(metaObject, CREATE_TIME, Date.class, date);
//		setInsertFieldValByName(CREATE_TIME, date, metaObject);
		//更新时间
		strictInsertFill(metaObject, UPDATE_TIME, Date.class, date);
//		setInsertFieldValByName(UPDATE_TIME, date, metaObject);
		//是否删除 默认为0 不删除
		strictInsertFill(metaObject, DEL_FLAG, Integer.class, YesOrNoEnum.NO.getValue());
//		setInsertFieldValByName(del_flag, YesOrNoEnum.NO.getValue(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		//更新者
		//setUpdateFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
		//strictInsertFill(metaObject, UPDATE_BY, Long.class, SecurityUser.getUserId());
		//更新时间
//		setUpdateFieldValByName(UPDATE_TIME, new Date(), metaObject);
		strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
	}
}
