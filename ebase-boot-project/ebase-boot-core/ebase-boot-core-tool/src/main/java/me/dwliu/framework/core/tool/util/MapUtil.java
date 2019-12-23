package me.dwliu.framework.core.tool.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    /**
     * 忽略大小写
     *
     * @param map
     * @param key
     * @param defaultVal :默认值
     * @return Object
     * @throws
     * @since 1.0.0
     */
    public static Object getIgnoreCase(Map<String, Object> map, String key, Object defaultVal) {
        for (String k : map.keySet()) {
            if (key.equalsIgnoreCase(k)) {
                return map.get(k);
            }
        }
        return defaultVal;
    }

    public static Object getIgnoreCase(Map<String, Object> map, String key) {
        return getIgnoreCase(map, key, null);
    }

    public static Map<String, Object> buildMap(String key, Object val) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, val);
        return map;
    }
}
