package io.spring.boot.common.db;

import java.util.Set;

/**
 * 
 * @since created on 2022年8月14日
 */
public class DynamicDataSourceContextHolder {

	public static Set<String> dataSourceIds;

	public static boolean containsDataSource(String dsId) {
		return false;
	}

	public static void setDataSourceType(String name) {
		
	}

	public static void clearDataSourceType() {
		
	}

}
