package me.dwliu.ebase.sample.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.dwliu.ebase.sample.dao.UserDAO;
import me.dwliu.ebase.sample.dto.UserDTO;
import me.dwliu.ebase.sample.entity.UserDO;
import me.dwliu.ebase.sample.service.UserService;
import me.dwliu.framework.core.base.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDAO, UserDO, UserDTO> implements UserService {
    @Override
    public QueryWrapper<UserDO> getWrapper(Map<String, Object> params) {

        String id = (String) params.get("id");

        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);


        return wrapper;
    }

//    @Transactional
//    @Override
//    public void insert(UserDO userDO) {
//        super.save(userDO);
//    }
//
//    @Override
//    public void insertBach(List<UserDO> userDOs) {
//        super.saveBatch(userDOs);
//    }
//
//    @Override
//    public void insertBach(List<UserDO> userDOs, int size) {
//        super.saveBatch(userDOs, size);
//    }
//
//    @Override
//    public boolean update(UserDTO userDTO) {
//        boolean b = this.updateEntity(userDTO);
//        return b;
//    }
//
//    @Override
//    public boolean deleteLogic(Long id) {
//        Long[] ids = new Long[]{1145618257814810626L, 1145618257974194177L};
//
//
//        boolean b = super.deleteEntityLogic(Arrays.asList(ids));
//        return b;
//    }

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
