package code.pocket.base.utils;

@FunctionalInterface
public interface Filter {
	boolean apply(Object obj);
}