package me.dwliu.framework.integration.log.feign;

import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.log.constant.LogConstant;
import me.dwliu.framework.core.log.dto.LogOperationDTO;
import me.dwliu.framework.core.security.constant.SecurityCoreConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 系统日志feign接口
 *
 * @author liudw
 * @date 2019-11-28 15:24
 **/
@FeignClient(contextId = "remoteSysLogOperationService", value = "ebase-service-log",
	fallback = SysLogOperationServiceFallback.class)
public interface RemoteSysLogOperationService {


	@PostMapping(LogConstant.API_PREFIX)
	Result<Boolean> save(@RequestBody LogOperationDTO entity,
	                     @RequestHeader(SecurityCoreConstant.FROM) String from);
}
