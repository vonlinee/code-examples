package io.devpl.spring.data.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * 切换线程绑定的数据源
 */
public final class SwitchDbInvoke {

	private static final Logger log = LoggerFactory.getLogger(SwitchDbInvoke.class);

	/**
	 * 持有当前数据源ID
	 */
	private static final ThreadLocal<String> holder = new ThreadLocal<>();

	private static final List<String> dataSourceIds = new ArrayList<>();

	public static <T> T invoke(String dataSoruceId, Supplier<T> supplier) {
		try {
			log.info("switch datasource to [{}]", dataSoruceId);
			return supplier.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setDataSourceType(String dataSourceType) {
		holder.set(dataSourceType);
	}

	public static void addOptionalDataSourceId(String... dataSourceId) {
		dataSourceIds.addAll(Arrays.asList(dataSourceId));
	}

	public static void addOptionalDataSourceId(Collection<String> dataSourceId) {
		dataSourceIds.addAll(dataSourceId);
	}

	public static String getDataSourceType() {
		return holder.get();
	}

	public static void clearDataSourceType() {
		holder.remove();
	}

	/**
	 * 判断指定DataSrouce当前是否存在
	 * 
	 * @param dataSourceId
	 */
	public static boolean contains(String dataSourceId) {
		return dataSourceIds.contains(dataSourceId);
	}
}
