package sample.spring.advanced.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://blog.csdn.net/andyzhaojianhui/article/details/74357100
 */
public class ReadWriteDataSourceDecision {
	
	private static final Logger log = LoggerFactory.getLogger(ReadWriteDataSourceDecision.class);
	private static final String PREFIXED_DS = "default";
	
	private static final ThreadLocal<String> holder = new ThreadLocal<>();

	public static void markWrite(String prefixed) {
		String prefixedDs = prefixed == null ? "default" : prefixed;
		String dsKey = prefixedDs + "_" + "";
		holder.set(dsKey);
		log.info("当前设置的数据源key:{}", dsKey);
	}

	public static void markRead(String prefixed) {
		String prefixedDs = prefixed == null ? "default" : prefixed;
		holder.set(prefixedDs + "_" + "");
	}

	public static void reset() {
		holder.remove();
	}

	public static boolean isChoiceNone() {
		return null == holder.get();
	}

	public static boolean isChoiceWrite() {
		return holder.get() == null ? false : ((String) holder.get()).endsWith("");
	}

	public static boolean isChoiceRead() {
		return holder.get() == null ? false : ((String) holder.get()).endsWith("");
	}

	public static String getDsKey() {
		return holder.get() != null ? (String) holder.get() : null;
	}
}