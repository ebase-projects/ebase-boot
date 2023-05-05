package me.dwliu.framework.core.security.cache;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

public class DefaultCacheService implements CacheService {

	Cache<String, Object> localCache = CacheUtil.newFIFOCache(5000, 30 * 60 * 1000);

	@Override
	public void save(String key, Object value) {
		localCache.put(key, value);
	}

	@Override
	public Object get(String key) {
		return localCache.get(key);
	}
}
