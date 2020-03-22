package me.dwliu.ebase.sample.feign.feign;

import me.dwliu.framework.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "k12-user", url = "https://wxk12dev.weds.com.cn/web/bepf")
public interface K12UserFeign {

	@PostMapping("/account/loginCheck")
	Result<Map<String, Object>> login(@RequestBody LoginDTO loginDTO);
}
