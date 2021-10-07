// package me.dwliu.framework.integration.security.feign.fallback;
//
// import feign.hystrix.FallbackFactory;
// import lombok.extern.slf4j.Slf4j;
// import me.dwliu.framework.common.constant.ServiceConstant;
// import me.dwliu.framework.common.model.Result;
// import me.dwliu.framework.core.security.entity.UserInfoDetails;
// import me.dwliu.framework.integration.security.feign.UserInfoDetailsFeignClient;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.stereotype.Component;
//
// /**
//  * 用户接口FallbackFactory
//  *
//  * @author liudw
//  * @date 2020/7/3 22:36
//  **/
// @Component
// @Slf4j
// public class UserInfoDetailsFallbackFactory implements FallbackFactory<UserInfoDetailsFeignClient> {
//
//
// 	// @Override
// 	// public UserInfoDetailsFeignClient create(Throwable throwable) {
// 	// 	log.error("{}", throwable);
// 	// 	return (username, from) -> new Result<>();
// 	// }
// }
