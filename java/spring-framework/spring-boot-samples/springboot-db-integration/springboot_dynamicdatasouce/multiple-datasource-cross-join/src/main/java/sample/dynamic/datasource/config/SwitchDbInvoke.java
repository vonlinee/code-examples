package sample.dynamic.datasource.config;

import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchDbInvoke {
	
	private static Logger log = LoggerFactory.getLogger(SwitchDbInvoke.class);

	/**
	 * 主数据源锁定
	 */
	private static ThreadLocal<String> dbMain = new ThreadLocal<>();

	@SuppressWarnings("hiding")
	public static <T> T setMainDb(String db, Supplier<T> supplier) {
		try {
			setMainDb(db);
			return supplier.get();
		} finally {
			resetMainDb();
		}
	}

	public static void setMainDb(String db) {
		dbMain.set(db);
		DataSourceDecision.decide(db);
	}

	public static void resetMainDb() {
		DataSourceDecision.reset();
		dbMain.remove();
	}

	@SuppressWarnings("hiding")
	public static <T> T invoke(String db, Supplier<T> supplier) {
		try {
			if (dbMain.get() != null) {
				if (!"tidb".equals(db) && !"ifc".equals(db) && !dbMain.get().equals(db)) {
					String errmsg = String.format("当前线程已锁定数据源[%s],禁止切换到[%s]", dbMain.get(), db);
					throw new RuntimeException(errmsg);
				}
			}
			DataSourceDecision.decide(db);
			return supplier.get();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (dbMain.get() != null) {
				DataSourceDecision.decide(dbMain.get());
			} else {
				DataSourceDecision.reset();
			}
		}
	}

	@SuppressWarnings("hiding")
	public static <T> T invokeTidb(Supplier<T> supplier) {
		return invoke("tidb", supplier);
	}

	public static void invokeTidb(ActionTemp action) {
		invoke("tidb", action);
	}

	public static void invoke(String db, ActionTemp action) {
		try {
			if (dbMain.get() != null) {
				if (!"tidb".equals(db) && !"ifc".equals(db) && !dbMain.get().equals(db)) {
					String errmsg = String.format("当前线程已锁定数据源[%s],禁止切换到[%s]", dbMain.get(), db);
					throw new RuntimeException(errmsg);
				}
			}
			DataSourceDecision.decide(db);
			action.doing();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (dbMain.get() != null) {
				DataSourceDecision.decide(dbMain.get());
			} else {
				DataSourceDecision.reset();
			}
		}
	}

	@FunctionalInterface
	public static interface ActionTemp {

		void doing();

	}
}
