package me.dwliu.ebase.sample.service;

import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.ebase.sample.vo.UserVO;
import me.dwliu.framework.core.base.page.PageData;
import me.dwliu.framework.core.base.service.BaseService;

import java.util.Map;

public interface UserService extends BaseService<UserDO, UserDTO> {
	PageData<UserDTO> listPage4Vo(Map<String, Object> params);

}
