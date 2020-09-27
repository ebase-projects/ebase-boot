package me.dwliu.framework.core.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.dwliu.framework.common.enums.YesOrNoEnum;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.core.security.utils.SecurityUtils;
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
	private final static String DEL_FLAG = "delFlag";

	@Override
	public void insertFill(MetaObject metaObject) {
		UserInfoDetails user = SecurityUtils.getUser();
		if (user != null) {
			//创建者
			strictInsertFill(metaObject, CREATE_BY, String.class, user.getUserId());
			//创建者所属部门
			//strictInsertFill(metaObject, DEPT_ID, Long.class, user.getDeptId());
			//更新者
			strictInsertFill(metaObject, UPDATE_BY, String.class, user.getUserId());
		}


		Date date = new Date(System.currentTimeMillis());
		//创建时间
		strictInsertFill(metaObject, CREATE_TIME, Date.class, date);
		//更新时间
		strictInsertFill(metaObject, UPDATE_TIME, Date.class, date);
		//是否删除 默认为0 不删除
		strictInsertFill(metaObject, DEL_FLAG, Integer.class, YesOrNoEnum.NO.getValue());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		UserInfoDetails user = SecurityUtils.getUser();
		if (user != null) {
			//更新者
			strictInsertFill(metaObject, UPDATE_BY, String.class, user.getUserId());
		}
		//更新时间
		strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
	}
}
