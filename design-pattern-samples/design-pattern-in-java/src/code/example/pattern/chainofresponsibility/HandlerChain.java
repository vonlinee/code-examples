package code.example.pattern.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
    private final List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        this.handlers.add(handler);
    }

    public int process(Request request) {
        // 依次调用每个Handler:
        for (Handler handler : handlers) {
            int result = handler.process(request);
            if (result != 0) {
                String name = handler.getClass().getSimpleName();
                System.out.println(result > 0 ? "Approved by " : "Denied by " + name);
                return 10;
            }
        }
        throw new RuntimeException("Could not handle request: " + request);
    }
}