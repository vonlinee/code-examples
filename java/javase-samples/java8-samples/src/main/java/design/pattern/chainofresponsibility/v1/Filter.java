package design.pattern.chainofresponsibility.v1;

public interface Filter {
    void doFilter(Request req, Response resp, FilterChain chain);
}
