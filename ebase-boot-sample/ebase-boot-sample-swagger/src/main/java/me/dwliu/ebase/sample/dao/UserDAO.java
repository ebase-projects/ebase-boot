package me.dwliu.ebase.sample.dao;

import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.framework.core.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface UserDAO extends BaseDAO<UserDO> {


    List<UserDO> getList(Map<String, Object> params);
}
