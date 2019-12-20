package me.dwliu.lab.core.log.api.feign;

import me.dwliu.lab.core.log.api.constant.LogConstant;
import me.dwliu.lab.core.log.api.dto.SysLogOperationDTO;
import me.dwliu.lab.core.tool.result.Result;
import me.dwliu.lab.security.constant.SecurityCoreConstant;
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
    Result<Boolean> save(@RequestBody SysLogOperationDTO entity,
                         @RequestHeader(SecurityCoreConstant.FROM) String from);
}
