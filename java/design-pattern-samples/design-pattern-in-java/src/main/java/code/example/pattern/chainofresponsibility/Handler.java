package code.example.pattern.chainofresponsibility;

public interface Handler {
    int process(Request request);
}