package me.dwliu.framework.plugin.security.feign;

import me.dwliu.framework.common.constant.ServiceConstant;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import me.dwliu.framework.core.security.entity.UserInfoDetails;
import me.dwliu.framework.plugin.security.feign.fallback.UserInfoDetailsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户接口
 *
 * @author liudw
 * @date 2020/7/3 22:36
 **/
@FeignClient(contextId = "UserInfoDetailsFeignClient",
	value = ServiceConstant.EBASE_SERVICE_SYSTEM, fallbackFactory = UserInfoDetailsFallbackFactory.class)
public interface UserInfoDetailsFeignClient {

	String API_PREFIX = "/system/user";


	/**
	 * 根据用户名获取用户信息
	 *
	 * @param username 用户名
	 * @param from     是否内部调用标志
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getByUsername")
	Result<UserInfoDetails> getByUsername(@RequestParam("username") String username,
	                                      @RequestHeader(SecurityCoreConstant.FROM) String from);


}
