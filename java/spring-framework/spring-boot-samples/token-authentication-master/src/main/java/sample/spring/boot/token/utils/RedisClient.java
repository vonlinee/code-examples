package sample.spring.boot.token.utils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisClient {

	private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static int defaultTime = 86400;

	public boolean setExpire(String key, long time) {
		try {
			if (time > 0L) {
				this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception var5) {
			logger.error("\u8bbe\u7f6e\u7f13\u5b58\u5931\u6548\u65f6\u95f4", var5);
			return false;
		}
	}

	public long getExpire(String key) {
		return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	public boolean hasKey(String key) {
		return this.redisTemplate.hasKey(key);
	}

	public void deleteKeys(String... keys) {
		if (keys != null && keys.length > 0) {
			if (keys.length == 1) {
				this.redisTemplate.delete(keys[0]);
			} else {
				this.redisTemplate.delete(Arrays.asList(keys));
			}
		}
	}

	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		return key == null ? null : (T) redisTemplate.opsForValue().get(key);
	}

	public String getString(String key) {
		Object value = get(key);
		return value == null ? "" : value.toString();
	}

	public boolean set(String key, Object value) {
		try {
			this.redisTemplate.opsForValue().set(key, value, (long) defaultTime, TimeUnit.SECONDS);
			return true;
		} catch (Exception var4) {
			logger.error("\u5b58\u5165\u7f13\u5b58(1\u5929\u6709\u6548)", var4);
			return false;
		}
	}

	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0L) {
				this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				this.set(key, value);
			}
			return true;
		} catch (Exception var6) {
			logger.error("\u5b58\u5165\u7f13\u5b58\u5e76\u8bbe\u7f6e\u8fc7\u671f\u65f6\u95f4", var6);
			return false;
		}
	}

	public boolean hset(String key, String item, Object value) {
		try {
			this.redisTemplate.opsForHash().put(key, item, value);
			this.setExpire(key, (long) defaultTime);
			return true;
		} catch (Exception var5) {
			logger.error("\u5411hash\u8868\u4e2d\u653e\u5165\u6570\u636e(1\u5929\u6709\u6548)", var5);
			return false;
		}
	}

	public boolean hset(String key, String item, Object value, long time) {
		try {
			this.redisTemplate.opsForHash().put(key, item, value);
			if (time > 0L) {
				this.setExpire(key, time);
			}

			return true;
		} catch (Exception var7) {
			logger.error("\u5411hash\u8868\u4e2d\u653e\u5165\u6570\u636e\u5e76\u8bbe\u7f6e\u8fc7\u671f\u65f6\u95f4",
					var7);
			return false;
		}
	}

	public Object hget(String key, String item) {
		return this.redisTemplate.opsForHash().get(key, item);
	}

	public void hdelAll(String key, Object... item) {
		this.redisTemplate.opsForHash().delete(key, item);
	}

	public boolean hHasKey(String key, String item) {
		return this.redisTemplate.opsForHash().hasKey(key, item);
	}

	public long setRemove(String key, Object... values) {
		try {
			return this.redisTemplate.opsForSet().remove(key, values);
		} catch (Exception var4) {
			logger.error("\u79fb\u9664hash\u8868\u4e2d\u653e\u5165\u6570\u636e", var4);
			return 0L;
		}
	}
}
