package me.dwliu.me.log;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.lab.core.log.annotation.LogAction;

@Slf4j
public class LogActionTest {

	@LogAction("logTest---")
	public void test() {
		log.info("test");
	}


	public static void main(String[] args) {
		LogActionTest logActionTest = new LogActionTest();
		logActionTest.test();
	}

}
