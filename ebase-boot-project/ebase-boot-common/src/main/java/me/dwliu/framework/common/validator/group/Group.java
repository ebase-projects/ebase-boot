package me.dwliu.framework.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果CreateGroup组失败，则UpdateGroup组不会再校验
 *
 * @author liudw
 * @email ldw4033 at 163.com
 * @date 2017-03-15 23:15
 */
@GroupSequence({CreateGroup.class, UpdateGroup.class})
public interface Group {

}
