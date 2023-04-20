package me.dwliu.ebase.sample.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.dwliu.ebase.sample.dao.UserDAO;
import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.ebase.sample.service.UserDOService;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.mybatis.constant.Constant;
import me.dwliu.framework.core.mybatis.query.QueryPageUtil;
import me.dwliu.framework.core.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDOServiceImpl extends BaseServiceImpl<UserDAO, UserDO> implements UserDOService {
	@Override
	public Wrapper<UserDO> getWrapper(Map<String, Object> params) {
		return null;
	}
//	@Override
//	public QueryWrapper<UserDO> getWrapper(Map<String, Object> params) {
//
//		String id = (String) params.get("id");
//		String username = (String) params.get("username");
//
//		QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
//		wrapper.eq(StringUtils.isNotBlank(id), "id", id);
//		wrapper.like(StringUtils.isNotBlank(username), "username", username);
//
//		return wrapper;
//	}


//    @Override
//    public PageData<UserDTO> page(Map<String, Object> params) {
//
//        //方法一
//        IPage<UserDO> userDOs = super.baseDao.selectPage(getPage(params, Constant.CREATE_DATE, true), getWrapper(params));
//
//        PageData<UserDTO> pageData = getPageData(userDOs, UserDTO.class);
//
//        return pageData;
//
//        //方法二
//
////        //分页
////        IPage<UserDO> page = getPage(params, Constant.CREATE_DATE, false);
////
////        //查询
////        List<UserDO> list = baseDao.getList(params);
////
////        return getPageData(list, page.getTotal(), UserDO.class);
//
//
//    }


	@Override
	public PageData<UserDTO> page(Map<String, Object> params) {

		//方法一
//		IPage<UserDO> userDOs = super.baseDAO.selectPage(getPage(params, Constant.CREATE_DATE, true), getWrapper(params));
//		PageData<UserDTO> pageData = getPageData(userDOs, UserDTO.class);
//		return pageData;

		//方法二
        //分页
        IPage<UserDTO> page = new QueryPageUtil<UserDTO>().getPage(params, Constant.CREATE_DATE, false);
        //查询
        List<UserDTO> list = baseDAO.getUserDTOList(params);

        return getPageData(list, page.getTotal());


	}

//    private QueryWrapper<UserDO> getWrapper(Map<String, Object> params) {
//        String account = (String) params.get("account");
//
//
//        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
//        wrapper.eq(StringUtils.isNotBlank(account), "account", account);
//
//        return wrapper;
//    }


}
