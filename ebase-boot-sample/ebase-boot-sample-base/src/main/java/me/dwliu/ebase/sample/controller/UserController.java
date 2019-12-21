package me.dwliu.ebase.sample.controller;

import io.swagger.annotations.Api;
import me.dwliu.ebase.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author liudw
 * @date 2019-07-03 09:31
 **/
@RestController
@RequestMapping("user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("page")
//    @ApiOperation("分页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int", example = "1"),
//            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int", example = "10"),
//            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "account", value = "用户名", paramType = "query", dataType = "string")
//    })
//    public Result<PageData<UserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
//        PageData<UserDTO> page = userService.page(params);
//
//        return Result.success(page);
//    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
