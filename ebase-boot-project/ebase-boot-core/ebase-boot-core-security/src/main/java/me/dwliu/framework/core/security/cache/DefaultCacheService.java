package me.dwliu.framework.core.security.cache;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
public class DefaultCacheService implements CacheService {
	private  Integer expiration ;


	public DefaultCacheService(Integer expiration) {
		this.expiration = expiration;
	}

	Cache<String, Object> localCache = CacheUtil.newFIFOCache(5000, expiration);

	@Override
	public void save(String key, Object value) {
		localCache.put(key, value);
	}

	@Override
	public Object get(String key) {
		return localCache.get(key);
	}
}
