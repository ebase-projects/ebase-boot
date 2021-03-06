package me.dwliu.ebase.sample.mongodb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dwliu.ebase.sample.mongodb.dto.UserDTO;
import me.dwliu.ebase.sample.mongodb.service.UserService;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.common.validator.ValidatorUtils;
import me.dwliu.framework.common.validator.group.CreateGroup;
import me.dwliu.framework.common.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

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

//	@GetMapping("page")
//	@ApiOperation("分页")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int", example = "1"),
//		@ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int", example = "10"),
//		@ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "string"),
//		@ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "string"),
//		@ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "string")
//	})
//	public Result<PageData<UserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
//		PageData<UserDTO> page = userService.listEntityByPage(params);
//
//		return Result.success(page);
//	}
//
//


	@GetMapping("{id}")
	@ApiOperation("信息")
	public Result<UserDTO> get(@PathVariable("id") Long id) {
		UserDTO data = userService.getEntity(id);

		return Result.success(data);
	}

	@PostMapping
	@ApiOperation("保存")
	public Result save(@RequestBody UserDTO dto) {
		//效验数据
		ValidatorUtils.validateEntity(dto, CreateGroup.class, Default.class);

		userService.insertEntity(dto);

		return Result.success();
	}

	@PutMapping
	@ApiOperation("修改")
	public Result update(@RequestBody UserDTO dto) {
		//效验数据
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, Default.class);

		userService.updateEntity(dto);

		return Result.success();
	}

	@DeleteMapping
	@ApiOperation("删除")
	public Result delete(@RequestBody Long[] ids) {
		//效验数据
		//AssertUtils.isArrayEmpty(ids, "id");

		userService.deleteBachEntity(ids);

		return Result.success();
	}

//	@GetMapping("export")
//	@ApiOperation("导出")
//	public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
//		List<UserDTO> userDTOS = userService.listEntity(params);
//
//		ExcelUtil.exportExcelToTarget(response, null, userDTOS, UserExcel.class);
//	}


}
