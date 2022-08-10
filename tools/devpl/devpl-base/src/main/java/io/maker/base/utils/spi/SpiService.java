package io.maker.base.utils.spi;

import io.maker.base.utils.Lists;
import io.maker.base.utils.Ordered;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class SpiService {

	public static <S> S loadFirst(Class<S> clazz) {
		Iterator<S> iterator = loadAll(clazz);
		if (!iterator.hasNext()) {
			throw new IllegalStateException(String.format(
					"No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
					clazz.getName()));
		}
		return iterator.next();
	}

	public static <S> Iterator<S> loadAll(Class<S> clazz) {
		ServiceLoader<S> loader = ServiceLoader.load(clazz, Thread.currentThread().getContextClassLoader());
		return loader.iterator();
	}

	public static <S extends Ordered> List<S> loadAllOrdered(Class<S> clazz) {
		Iterator<S> iterator = loadAll(clazz);
		List<S> candidates = Lists.newArrayList(iterator);
		candidates.sort(Comparator.comparingInt(Ordered::getOrder));
		return candidates;
	}

	public static <S extends Ordered> S loadPrimary(Class<S> clazz) {
		List<S> candidates = loadAllOrdered(clazz);
		if (candidates.isEmpty()) {
			throw new IllegalStateException(String.format(
					"No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
					clazz.getName()));
		}
		return candidates.get(0);
	}
}
