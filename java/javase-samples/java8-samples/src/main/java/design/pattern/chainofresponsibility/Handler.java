package design.pattern.chainofresponsibility;

public interface Handler {
    int process(Request request);
}