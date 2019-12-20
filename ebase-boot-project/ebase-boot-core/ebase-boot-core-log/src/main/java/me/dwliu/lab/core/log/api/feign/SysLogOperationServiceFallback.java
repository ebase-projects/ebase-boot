package me.dwliu.lab.core.log.api.feign;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.lab.core.log.api.dto.SysLogOperationDTO;
import me.dwliu.lab.core.tool.result.Result;
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
