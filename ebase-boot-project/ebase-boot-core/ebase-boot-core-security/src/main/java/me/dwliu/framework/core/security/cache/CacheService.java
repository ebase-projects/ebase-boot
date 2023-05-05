package me.dwliu.framework.core.security.cache;

/**
 * 缓存接口生命
 *
 * @author liudw
 * @date 2023/5/5 11:10
 **/
public interface CacheService {
	void save(String key, Object value);

	Object get(String key);
}
