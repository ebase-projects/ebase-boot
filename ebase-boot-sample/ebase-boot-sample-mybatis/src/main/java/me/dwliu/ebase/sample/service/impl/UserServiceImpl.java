package me.dwliu.ebase.sample.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.dwliu.ebase.sample.dao.UserDAO;
import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.ebase.sample.service.UserService;
import me.dwliu.ebase.sample.vo.UserVO;
import me.dwliu.framework.common.model.PageData;
import me.dwliu.framework.core.datascope.annotation.DataScopeFilter;
import me.dwliu.framework.core.mybatis.page.QueryPageUtil;
import me.dwliu.framework.core.mybatis.service.impl.BaseServiceImpl;
import me.dwliu.framework.core.tool.convert.ConvertUtil;
import me.dwliu.framework.core.tool.util.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDAO, UserDO, UserDTO> implements UserService {
    @Override
    public QueryWrapper<UserDO> getWrapper(Map<String, Object> params) {

        String id = (String) params.get("id");
        String username = (String) params.get("username");

        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.like(StringUtils.isNotBlank(username), "username", username);

        return wrapper;
    }

    @Override
    public PageData<UserDTO> listPage4Vo(Map<String, Object> params) {
        IPage<UserVO> page = new QueryPageUtil<UserVO>().getPage(params, "id", true);
        List<UserVO> userVOS = this.baseDAO.listPage4Vo(params);

        PageData<UserDTO> pageData = getPageData(userVOS, page.getTotal(), UserDTO.class);

        return pageData;
    }

    @Override
    // @DataScopeFilter
    public PageData<UserDTO> listUserByPage(Map<String, Object> params) {
        return this.listEntityByPage(params);

    }

    @Override
    public PageData<UserDTO> listUserDTOByPage(Map<String, Object> params) {

        IPage<UserDO> page = new QueryPageUtil<UserDO>().getPage(params, "id", true);
        List<UserDO> list = this.baseDAO.getList(params);

        PageData<UserDTO> pageData = getPageData(list, page.getTotal(), UserDTO.class);

        return pageData;
    }


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
