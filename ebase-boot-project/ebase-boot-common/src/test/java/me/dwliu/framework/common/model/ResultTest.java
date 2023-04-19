package me.dwliu.framework.common.model;

import me.dwliu.framework.common.code.SystemResultCode;
import org.junit.jupiter.api.Test;

class ResultTest {

	@Test
	void getData() {
	}

	@Test
	void success() {
		Result<String> result = Result.success();
		System.out.println(result);
	}

	@Test
	void testSuccess() {
		String str="返回成功数据";
		Result<String> result = Result.success(str);
		System.out.println(result);

	}

	@Test
	void status() {
//		Result<String> result = Result.status(true,"成功");
		Result<String> result = Result.status(false,"失败");
		System.out.println(result);
	}

	@Test
	void testStatus() {
		Result<String> result = Result.status(false, SystemResultCode.DATA_ADD_FAILED);
		System.out.println(result);
	}

	@Test
	void fail() {
	}

	@Test
	void testFail() {
	}

	@Test
	void testFail1() {
	}

	@Test
	void testFail2() {
	}

	@Test
	void getCode() {
	}

	@Test
	void getMsg() {
	}

	@Test
	void getTimestamp() {
	}

	@Test
	void testGetData() {
	}

	@Test
	void setCode() {
	}

	@Test
	void setMsg() {
	}

	@Test
	void setTimestamp() {
	}

	@Test
	void setData() {
	}

	@Test
	void testToString() {
	}
}
