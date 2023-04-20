package me.dwliu.ebase.sample.service;

import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.service.BaseService;

import java.util.Map;

public interface UserDOService extends BaseService<UserDO> {
	PageData<UserDTO> page(Map<String, Object> params);
}
