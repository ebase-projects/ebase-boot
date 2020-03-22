package me.dwliu.ebase.sample.feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hello", url = "http://localhost:8080")
public interface HelloFeignService {

	@GetMapping("/test")
	Hello hello(@SpringQueryMap Hello hello);
}
