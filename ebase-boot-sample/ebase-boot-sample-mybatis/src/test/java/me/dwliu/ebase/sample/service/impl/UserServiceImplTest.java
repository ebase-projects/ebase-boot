package me.dwliu.ebase.sample.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.ebase.sample.dto.RoleDTO;
import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.service.UserService;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.constant.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	public void insert() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("asdf1");
		userDTO.setEmail("ldw4033@163.com");

		List<RoleDTO> roleDTOs = new ArrayList<>();

		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleId(111L);
		roleDTO.setRolename("test");

		RoleDTO roleDTO2 = new RoleDTO();
		roleDTO2.setRoleId(222L);
		roleDTO2.setRolename("test2");

		roleDTOs.add(roleDTO);
		roleDTOs.add(roleDTO2);

		userDTO.setRoleList(roleDTOs);


		boolean b = userService.insertEntity(userDTO);
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
			roleDTO.setRoleId(111L + i);
			roleDTO.setRolename("test" + i);

			roleDTOs.add(roleDTO);

			userDTO.setRoleList(roleDTOs);


		}

		List<UserDTO> userDTOS = userService.insertBachEntityReturnId(userDTOList, 4);
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
		userDTO.setUserId(1164760405986263041L);
		userDTO.setUsername("updateName");
		userDTO.setRealName("updatewwwwwName");
		userDTO.setEmail("ldw4033@qq.com");

		userService.updateEntity(userDTO);
	}

	@Test
	public void delete() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(1164760405986263041L);
		userDTO.setUsername("updateName");
		userDTO.setRealName("updatewwwwwName");
		userDTO.setEmail("ldw4033@qq.com");

		userService.deleteEntity(userDTO.getUserId());
	}

	@Test
	public void get() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(1164760405986263041L);
//        userDTO.setUsername("updateName");
//        userDTO.setRealName("updatewwwwwName");
//        userDTO.setEmail("ldw4033@qq.com");


		UserDTO entity = userService.getEntity(1164761415639158785L);
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
		userService.deleteBachEntity(ids);
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


		PageData<UserDTO> page = userService.listEntityByPage(params);
		log.info(page.toString());
	}
}
