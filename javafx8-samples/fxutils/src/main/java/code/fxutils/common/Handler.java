package code.fxutils.common;

@FunctionalInterface
public interface Handler<T, R> {
	R apply(T input);
}
