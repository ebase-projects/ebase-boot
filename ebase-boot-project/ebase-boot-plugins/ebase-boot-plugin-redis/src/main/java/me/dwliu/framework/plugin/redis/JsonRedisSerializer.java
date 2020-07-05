package me.dwliu.framework.plugin.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


/**
 * Redis序列化
 *
 * @author liudw
 * @date 2020/7/5 22:31
 **/
public class JsonRedisSerializer<T> implements RedisSerializer<T> {
	private static ParserConfig defaultRedisConfig = new ParserConfig();

	static {
		defaultRedisConfig.setAutoTypeSupport(true);
	}

	private Class<T> type;

	public JsonRedisSerializer(Class<T> type) {
		this.type = type;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(IOUtils.UTF8);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, IOUtils.UTF8);

		return JSON.parseObject(str, type, defaultRedisConfig);
	}
}
