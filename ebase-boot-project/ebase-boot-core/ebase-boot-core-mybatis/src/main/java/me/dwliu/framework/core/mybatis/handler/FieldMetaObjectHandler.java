package me.dwliu.framework.core.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.dwliu.framework.common.enums.YesOrNoEnum;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.core.security.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;


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
            if (metaObject.hasSetter(CREATE_BY)) {
                this.strictInsertFill(metaObject, CREATE_BY, Long.class, Long.parseLong(user.getUserId()));
            }
            //创建者所属部门
            if (metaObject.hasSetter(CREATE_DEPT)) {
                this.strictInsertFill(metaObject, CREATE_DEPT, Long.class, Long.parseLong(user.getDeptId()));
            }
            //更新者
            if (metaObject.hasSetter(UPDATE_BY)) {
                this.strictInsertFill(metaObject, UPDATE_BY, Long.class, Long.parseLong(user.getUserId()));
            }
        }

        LocalDateTime date = LocalDateTime.now();
        //创建时间
        if (metaObject.hasSetter(CREATE_TIME)) {
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, date);
        }
        //更新时间
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, date);
        }
        //是否删除 默认为0 不删除
        if (metaObject.hasSetter(DEL_FLAG)) {
            this.strictInsertFill(metaObject, DEL_FLAG, Integer.class, YesOrNoEnum.NO.getValue());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        UserInfoDetails user = SecurityUtils.getUser();
        if (user != null) {
            //更新者
            if (metaObject.hasSetter(UPDATE_BY)) {
                if (null != getFieldValByName(UPDATE_BY, metaObject)) {
                    //bugfix 由于更新字段有值，更新时间不生效
                    this.setFieldValByName(UPDATE_BY, Long.parseLong(user.getUserId()), metaObject);
                } else {
                    this.strictInsertFill(metaObject, UPDATE_BY, Long.class, Long.parseLong(user.getUserId()));
                }
            }

        }


        //更新时间
        if (metaObject.hasSetter(UPDATE_TIME)) {
            if (null != getFieldValByName(UPDATE_TIME, metaObject)) {
                //bugfix 由于更新字段有值，更新时间不生效
                this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
            } else {
                this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
            }
        }

    }
}
