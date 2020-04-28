package me.dwliu.ebase.sample.mongodb.service;

import me.dwliu.ebase.sample.mongodb.dto.UserDTO;

/**
 * 用户服务
 *
 * @author liudw
 * @date 2020/4/28 18:51
 **/
public interface UserService {

	UserDTO getEntity(Long id);

	void insertEntity(UserDTO dto);

	void updateEntity(UserDTO dto);

	void deleteBachEntity(Long[] ids);


}
