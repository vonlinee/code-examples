package code.example.pattern.chainofresponsibility;

import java.math.BigDecimal;

public class ManagerHandler implements Handler {
    @Override
    public int process(Request request) {
        // 如果超过1000元，处理不了，交下一个处理:
        if (request.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            return 0;
        }
        // 对Bob有偏见:
        if (request.getName().equalsIgnoreCase("bob")) {
            return -1;
        }
        return 1;
    }
}