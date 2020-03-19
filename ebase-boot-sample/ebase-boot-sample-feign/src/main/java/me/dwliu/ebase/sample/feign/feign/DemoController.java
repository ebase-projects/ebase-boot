package me.dwliu.ebase.sample.feign.feign;

import me.dwliu.framework.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DemoController {
	@Autowired
	private FeignClientProxy feignClientProxy;

//	@GetMapping("/test")
//	public Result<BeVisitorInfoDTO> test() {
//		Result<BeVisitorInfoDTO> list = feignClientProxy.getList(1, 10);
//
//		return list;
//
//	}
	@GetMapping("/test1")
	public Result<BeVisitorInfoDTO> test1() {
		Result<BeVisitorInfoDTO> one = feignClientProxy.getOne(1229926019503329282L);

		return one;

	}
}
