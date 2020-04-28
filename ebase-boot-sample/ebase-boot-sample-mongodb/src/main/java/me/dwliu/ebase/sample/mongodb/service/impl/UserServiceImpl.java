package me.dwliu.ebase.sample.mongodb.service.impl;


import ch.qos.logback.core.pattern.ConverterUtil;
import me.dwliu.ebase.sample.mongodb.dao.UserDAO;
import me.dwliu.ebase.sample.mongodb.dto.UserDTO;
import me.dwliu.ebase.sample.mongodb.entity.UserDO;
import me.dwliu.ebase.sample.mongodb.service.UserService;
import me.dwliu.framework.core.tool.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;


	@Override
	public UserDTO getEntity(Long id) {
		return null;
	}

	@Override
	public void insertEntity(UserDTO dto) {
		UserDO userDO = ConvertUtils.sourceToTarget(dto, UserDO.class);
		userDAO.insert(userDO);
	}

	@Override
	public void updateEntity(UserDTO dto) {

	}

	@Override
	public void deleteBachEntity(Long[] ids) {

	}
}
