package me.dwliu.framework.core.log.api.feign;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.log.api.dto.SysLogOperationDTO;
import org.springframework.stereotype.Component;

/**
 * 日志远程服务降级
 *
 * @author liudw
 * @date 2019-11-29 13:29
 **/
@Slf4j
@Component
public class SysLogOperationServiceFallback implements RemoteSysLogOperationService {


    @Override
    public Result<Boolean> save(SysLogOperationDTO entity, String from) {
        log.error("SysLogServiceFallback: 日志保存失败");
        return Result.fail("日志保存失败");
    }
}
