package me.dwliu.ebase.sample.feign.feign;

import me.dwliu.framework.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "feignClientProxy", url = "http://10.0.0.110:8094")
public interface FeignClientProxy {

//	@GetMapping("/V2/visitorInfo/page")
//	@ResponseBody
//	public Result<BeVisitorInfoDTO> getList(@RequestParam Integer limit, @RequestParam Integer page);

	@GetMapping("/V2/visitorInfo/{id}")
	@ResponseBody
	public Result<BeVisitorInfoDTO> getOne(@PathVariable(value = "id") Long id);
}
