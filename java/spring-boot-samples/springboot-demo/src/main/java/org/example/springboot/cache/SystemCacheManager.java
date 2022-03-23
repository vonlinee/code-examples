package org.example.springboot.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class SystemCacheManager {
	
//	@Autowired
	private CacheManager cacheManager;
//	@Autowired
	private Cache cache;
	
	private Map<String, Object> tableMap = new HashMap<String, Object>();
	
	public void put(String name, Object cacheItem) {
		this.tableMap.put(name, cacheItem);
	}
}
