package me.dwliu.framework.integration.security.feign;

import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 用户接口
 *
 * @author liudw
 * @date 2020/7/3 22:36
 **/

@HttpExchange
public interface UserInfoDetailsWebClient {

    String API_PREFIX = "/system/user";

    /**
     * 根据用户ID 获取用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @GetExchange(API_PREFIX + "/getUserById")
    Result<UserInfoDetails> getUserById(@RequestParam("userId") Long userId);

    /**
     * 根据用户手机号码获取用户信息
     *
     * @param mobile 手机号码
     * @return
     */
    @GetExchange(API_PREFIX + "/getUserByMobile")
    Result<UserInfoDetails> getUserByMobile(@RequestParam("mobile") String mobile);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @param from     是否内部调用标志
     * @return
     */
    @GetExchange(API_PREFIX + "/getUserByUseranme")
    Result<UserInfoDetails> getUserInfo(@RequestParam("username") String username,
                                        @RequestHeader(SecurityCoreConstant.FROM) String from);

}
