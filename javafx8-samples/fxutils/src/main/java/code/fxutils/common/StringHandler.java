package code.fxutils.common;

@FunctionalInterface
public interface StringHandler<R> extends Handler<String, R>{
	R apply(String input);
}