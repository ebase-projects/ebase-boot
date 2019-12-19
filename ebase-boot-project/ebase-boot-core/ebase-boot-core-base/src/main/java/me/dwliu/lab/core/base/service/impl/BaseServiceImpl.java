package me.dwliu.lab.core.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.dwliu.lab.core.base.constant.Constant;
import me.dwliu.lab.core.base.dao.BaseDAO;
import me.dwliu.lab.core.base.page.PageData;
import me.dwliu.lab.core.base.service.BaseService;
import me.dwliu.lab.core.tool.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 基础服务类，所有Service都要继承
 *
 * @param <M> DAO
 * @param <T> DO
 * @param <D> DTO
 * @author eric
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<M extends BaseDAO<T>, T, D> extends ServiceImpl<M, T>
        implements BaseService<T, D> {

    @Autowired
    protected M baseDAO;

    /**
     * 查询条件封装
     *
     * @param params
     * @return
     */
    public abstract QueryWrapper<T> getWrapper(Map<String, Object> params);

    @Override
    public PageData<D> listEntityByPage(Map<String, Object> params) {

//        IPage<T> page = baseDAO.selectPage(getPage(params, null, false), getWrapper(params));
//        return getPageData(page, currentDTOClass());

        return listEntityByPage(params, null, false);
    }

    @Override
    public PageData<D> listEntityByPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        IPage<T> page = baseDAO.selectPage(getPage(params, defaultOrderField, isAsc), getWrapper(params));

        return getPageData(page, currentDTOClass());
    }

    @Override
    public List<D> listEntity(Map<String, Object> params) {
        List<T> entityList = baseDAO.selectList(getWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, currentDTOClass());
    }


    /**
     * 获取分页对象
     *
     * @param params            分页查询参数
     * @param defaultOrderField 默认排序字段
     * @param isAsc             排序方式
     */
    protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(Constant.PAGE, page);

        //排序字段
        String orderField = (String) params.get(Constant.ORDER_FIELD);
        String order = (String) params.get(Constant.ORDER);

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField)) {
            if (StringUtils.isNotEmpty(order) && Constant.ASC.equalsIgnoreCase(order)) {
                page.addOrder(OrderItem.asc(orderField));
            } else if (StringUtils.isNotEmpty(order) && Constant.DESC.equalsIgnoreCase(order)) {
                page.addOrder(OrderItem.desc(orderField));
            } else {
                //默认升序
                page.addOrder(OrderItem.asc(orderField));
            }
        } else {
            //没有排序字段，则不排序
            if (StringUtils.isEmpty(defaultOrderField)) {
                return page;
            }
            //默认排序
            if (isAsc) {
                //page.setAsc(defaultOrderField);
                page.addOrder(OrderItem.asc(defaultOrderField));
            } else {
                //page.setDesc(defaultOrderField);
                page.addOrder(OrderItem.desc(defaultOrderField));
            }

        }


        return page;
    }

    protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target) {
        List<T> targetList = ConvertUtils.sourceToTarget(list, target);


        return new PageData<>(targetList, total);
    }

    protected <T> PageData<T> getPageData(IPage page, Class<T> target) {
        return getPageData(page.getRecords(), page.getTotal(), target);
    }

    /**
     * 参数模糊查询
     *
     * @param params 参数列表
     * @param likes  需要模糊查询的字段
     * @return Map<String, Object>
     */
    protected Map<String, Object> paramsToLike(Map<String, Object> params, String... likes) {
        for (String like : likes) {
            String val = (String) params.get(like);
            if (StringUtils.isNotEmpty(val)) {
                params.put(like, "%" + val + "%");
            } else {
                params.put(like, null);
            }
        }
        return params;
    }


//    /**
//     * 逻辑删除
//     *
//     * @param ids id集合(逗号分隔)
//     * @return
//     */
//    @Override
//    @Transactional
//    public boolean deleteEntityLogic(@NotEmpty List<Long> ids) {
////        T entity = BeanUtils.instantiateClass(modelClass);
//////        entity.setUpdateUser(user.getUserId());
////        //entity.setUpdateTime(LocalDateTime.now());
////        entity.setCreateDate(new Date());
////
////        //boolean update = super.update(entity, Wrappers.<T>update().lambda().in(T::getId, ids));
////        boolean update = super.update(entity, new QueryWrapper<T>().in("id", ids));
////
////        //boolean update = super.update(entity, new QueryWrapper<T>().lambda().in(T::getId, ids));
////        boolean remove = super.removeByIds(ids);
////        return update && remove;
//
//        return false;
//
//    }
}
