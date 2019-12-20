package me.dwliu.lab.core.log.config;

import lombok.AllArgsConstructor;
import me.dwliu.lab.core.log.aspect.LogActionAspect;
import me.dwliu.lab.core.log.event.LogActionListener;
import me.dwliu.lab.core.log.api.feign.RemoteSysLogOperationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动装载
 *
 * @author liudw
 * @date 2019-03-11 13:22
 **/
@Configuration
@AllArgsConstructor
@ComponentScan("me.dwliu.lab.core.log")
@ConditionalOnWebApplication
public class LogActionConfig {

    private final RemoteSysLogOperationService remoteSysLogOperationService;

    /**
     * 实例化日志监听
     *
     * @return
     */
    @Bean
    public LogActionListener logActionListener() {
        return new LogActionListener(remoteSysLogOperationService);
    }

    /**
     * 实例化操作日志AOP
     *
     * @return
     */
    @Bean
    public LogActionAspect logActionAspect() {
        return new LogActionAspect();
    }


}
