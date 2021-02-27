package me.dwliu.ebase.sample.feign.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DemoController {
	@Autowired
	private FeignClientProxy feignClientProxy;
	@Autowired
	private K12UserFeign k12UserFeign;
	@Autowired
	private HelloFeignService helloFeignService;

//	@GetMapping("/test")
//	public Result<BeVisitorInfoDTO> test() {
//		Result<BeVisitorInfoDTO> list = feignClientProxy.getList(1, 10);
//
//		return list;
//
//	}
// 	@GetMapping("/test1")
// 	public Result<BeVisitorInfoDTO> test1() {
// 		Result<BeVisitorInfoDTO> one = (Result<me.dwliu.ebase.sample.feign.feign.BeVisitorInfoDTO>) feignClientProxy.getOne(1229926019503329282L);
//
// 		return one;
//
// 	}

	// @GetMapping("/test2")
	// public Result<Map<String, Object>> test2() {
	// 	LoginDTO loginDTO=new LoginDTO();
	// 	loginDTO.setLogin_name("13166778899");
	// 	loginDTO.setPwd("C+Q8E8uHuQ3jy5Gw+BKdYA==");
	// 	Result<Map<String, Object>> login = k12UserFeign.login(loginDTO);
	//
	// 	return login;
	//
	// }
	//
	// @GetMapping("/test3")
	// public Result<Hello> test3() {
	// 	Hello p=new Hello();
	// 	p.setH1("13166778899");
	// 	p.setH2("C+Q8E8uHuQ3jy5Gw+BKdYA==");
	// 	Hello hello = helloFeignService.hello(p);
	//
	// 	return Result.success(hello);
	//
	// }
}
