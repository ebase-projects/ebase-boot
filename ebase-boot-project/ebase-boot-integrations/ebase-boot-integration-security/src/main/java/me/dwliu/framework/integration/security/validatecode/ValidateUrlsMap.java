package me.dwliu.framework.integration.security.validatecode;

import me.dwliu.framework.integration.security.validatecode.enums.ValidateCodeTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class ValidateUrlsMap {
	private Map<String, ValidateCodeTypeEnum> map;
	private static ValidateUrlsMap singleton;

	private ValidateUrlsMap() {
		map = new HashMap<>();
	}

	public static ValidateUrlsMap getValidateUrlsMap() {
		if (singleton == null) {
			singleton = new ValidateUrlsMap();
		}
		return singleton;
	}

	public void put(String key, ValidateCodeTypeEnum value) {
		map.put(key, value);
	}


	public Map<String, ValidateCodeTypeEnum> getMap() {
		return map;
	}

}
