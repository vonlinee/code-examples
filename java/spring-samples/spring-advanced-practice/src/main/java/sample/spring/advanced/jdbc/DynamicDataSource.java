package sample.spring.advanced.jdbc;

import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * DynamicDataSource继承自javax.sql.DataSource
 * 实现动态数据源
 * @author https://www.xttblog.com/?p=2025
 */
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * @param targetDataSources
	 */
	public DynamicDataSource(Map<Object, Object> targetDataSources) {
		
	}
	
	/**
	 * 选择多个数据源的KEY,一般是从线程绑定的事务上下文中获取，可以是任意的KEY，
	 * 但是必须和resolveSpecifiedLookupKey方法的返回值相匹配
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return null;
	}

	@Override
	protected Object resolveSpecifiedLookupKey(Object lookupKey) {
		//默认实现是将lookupKey直接返回
		Object key = super.resolveSpecifiedLookupKey(lookupKey);
		return "ds" + key;
	}
}
