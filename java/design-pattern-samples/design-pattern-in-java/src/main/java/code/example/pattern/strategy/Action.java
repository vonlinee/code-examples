package code.example.pattern.strategy;

@FunctionalInterface
public interface Action<I, O> {
    O run(I input);
}