package design.pattern.strategy;

@FunctionalInterface
public interface Action<I, O> {
    O run(I input);
}