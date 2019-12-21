package me.dwliu.lab.core.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.lab.common.model.Result;
import me.dwliu.lab.core.log.api.dto.SysLogOperationDTO;
import me.dwliu.lab.core.log.api.feign.RemoteSysLogOperationService;
import me.dwliu.lab.security.constant.SecurityCoreConstant;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 日志操作监听器
 *
 * @author liudw
 * @date 2019-02-25 17:03
 **/

@Slf4j
@AllArgsConstructor
//@EnableAsync
public class LogActionListener {

    private RemoteSysLogOperationService remoteSysLogOperationService;


    @Async("taskAsyncExecutor")
    @EventListener(LogActionEvent.class)
    public void saveLog(LogActionEvent event) {
        log.info("日志监听器保存操作");

        Map<String, Object> logAction = (Map<String, Object>) event.getSource();

        logAction.get("logAction");

        SysLogOperationDTO sysLogOperationDTO = (SysLogOperationDTO) logAction.get("logAction");

        log.info("{}", logAction);

        Result<Boolean> result = remoteSysLogOperationService.save(sysLogOperationDTO, SecurityCoreConstant.FROM_IN);
        log.info("result:「{}」", result);

    }
}
