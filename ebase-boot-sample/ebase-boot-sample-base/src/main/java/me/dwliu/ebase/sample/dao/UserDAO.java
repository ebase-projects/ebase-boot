package me.dwliu.ebase.sample.dao;

import me.dwliu.ebase.sample.vo.UserVO;
import me.dwliu.framework.core.base.dao.BaseDAO;
import me.dwliu.ebase.sample.entity.UserDO;
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

	List<UserVO> listPage4Vo(Map<String, Object> params);

}
