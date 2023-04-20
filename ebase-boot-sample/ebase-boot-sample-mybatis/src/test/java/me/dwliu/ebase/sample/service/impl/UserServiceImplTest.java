package me.dwliu.ebase.sample.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.ebase.sample.dto.RoleDTO;
import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.entity.RoleDO;
import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.ebase.sample.service.UserDOService;
import me.dwliu.ebase.sample.service.UserService;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.constant.Constant;
import me.dwliu.framework.core.mybatis.query.QueryParamsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDOService userDOService;

	@Test
	public void insert() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("asdf1");
		userDTO.setEmail("ldw4033@163.com");

		List<RoleDTO> roleDTOs = new ArrayList<>();

		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(111L);
		roleDTO.setRolename("test");

		RoleDTO roleDTO2 = new RoleDTO();
		roleDTO2.setId(222L);
		roleDTO2.setRolename("test2");

		roleDTOs.add(roleDTO);
		roleDTOs.add(roleDTO2);

		userDTO.setRoleList(roleDTOs);


		boolean b = userService.insertDTO(userDTO);
	}

	@Test
//	@Transactional
	public void insertDO() {
		UserDO userDO = new UserDO();
//		userDO.setId(1l);
		userDO.setUsername("asdf1");
		userDO.setPassword(UUID.randomUUID().toString().replace("-", ""));
		userDO.setRealName("真实姓名");
		userDO.setAvatar("头像");
		userDO.setGender(1);
		userDO.setEmail("ldw4033@163.com");
		userDO.setMobile("18105351756");
		userDO.setDeptId(11l);
		userDO.setSuperAdmin(1);
		userDO.setStatus(1);
		userDO.setDeptName("1");

//		userDO.setCreateDept();
//		userDO.setCreateBy();
//		userDO.setCreateTime();
//		userDO.setUpdateBy();
//		userDO.setUpdateTime();
//		userDO.setDelFlag();
//		userDO.setRevision();

//		boolean save = userDOService.save(userDO);

		boolean save1 = userService.save(userDO);


		System.out.println(save1);


	}


	@Test
	public void insertBach() {
		List<UserDTO> userDTOList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername("atest11" + i);
			userDTO.setEmail("ldw4033@163.com");
			userDTOList.add(userDTO);
			List<RoleDTO> roleDTOs = new ArrayList<>();

			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(111L + i);
			roleDTO.setRolename("test" + i);

			roleDTOs.add(roleDTO);

			userDTO.setRoleList(roleDTOs);


		}

		List<UserDTO> userDTOS = userService.insertBachDTOReturnId(userDTOList, 4);
		System.out.println(userDTOS);

	}


	@Test
	public void insertDOBach() {
		List<UserDO> userDOList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			UserDO userDO = new UserDO();
			userDO.setUsername("atest11" + i);
			userDO.setEmail("ldw4033@163.com");
			userDOList.add(userDO);

		}

		userDOService.saveBatch(userDOList, 4);
		System.out.println(userDOList);
	}

//    @Test
//    public void insertBach1() {
//        List<UserDTO> userDTOList = new ArrayList<>();
//
//        for (int i = 0; i < 100; i++) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setAccount("batch" + i);
//            userDTO.setEmail("ldw4033@163.com");
//            userDTOList.add(userDTO);
//
//        }
//
//        userService.insertBach(userDTOList, 30);
//    }

	@Test
	public void updateById() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1164760405986263041L);
		userDTO.setUsername("updateName");
		userDTO.setRealName("updatewwwwwName");
		userDTO.setEmail("ldw4033@qq.com");

		userService.updateDTO(userDTO);
	}

	@Test
	public void delete() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1164760405986263041L);
		userDTO.setUsername("updateName");
		userDTO.setRealName("updatewwwwwName");
		userDTO.setEmail("ldw4033@qq.com");

		userService.removeById(userDTO.getId());
	}

	@Test
	public void get() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(1164760405986263041L);
//        userDTO.setUsername("updateName");
//        userDTO.setRealName("updatewwwwwName");
//        userDTO.setEmail("ldw4033@qq.com");


		UserDTO entity = userService.getDTO(1164761415639158785L);
		System.out.println(entity);
	}


	@Test
	public void deleteLogic() {
		Long[] ids = {1164788766615879682L,
			1164788766804623361L,
			1164788766829789185L,
			1164788766850760706L,
			1164788766875926529L,
			1164788766905286657L,
			1164788766926258178L,
			1164788766955618306L,
			1164788766984978433L,
			1164788767001755650L,
			1164788767018532865L,
			1164788767039504386L,
			1164788767060475906L,
			1164788767085641729L,
			1164788767106613249L,
			1164788767127584769L,
			1164788767156944898L,
			1164788767177916418L,
			1164788767198887938L,
			1164788767215665154L,
			1164788767232442369L};
		userService.removeBatchByIds(Arrays.asList(ids));
//        log.info("删除成功：{}", b);

	}

	@Test
	public void update() {
	}

	@Test
	public void page() {
		Map<String, Object> params = new HashMap<>();
		params.put(Constant.PAGE, "1");
		params.put(Constant.LIMIT, "10");

		params.put(Constant.ORDER, Constant.ASC);
		params.put(Constant.ORDER_FIELD, "id");
//        params.put("account", "updateName");
		params.put("username", "atest");

		params.put("beginTime","2023-03-01");
		params.put("endTime","2023-03-31");

		PageData<UserDTO> page = userService.listDTOByPage(params);
		log.info(page.toString());
	}



	@Test
	public void page2() {
		Map<String, Object> params = new HashMap<>();
		params.put(Constant.PAGE, "1");
		params.put(Constant.LIMIT, "10");
		params.put(Constant.ORDER, Constant.ASC);
		params.put(Constant.ORDER_FIELD, "createTime");
		params.put("username", "atest");
		params.put("beginTime","2023-03-01");
		params.put("endTime","2023-03-31");

		QueryParamsUtil.paramsToBeginAndEndDate(params);
		QueryParamsUtil.paramsToLike(params,"username");

		PageData<UserDTO> page = userDOService.page(params);
		log.info(page.toString());
	}
}
