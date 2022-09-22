package log;

import java.util.HashMap;
import java.util.Map;

public class CheckThenActExample {

    private static final Map<String, String> map = new HashMap<>();

    public static void checkThenAct(Map<String, String> sharedMap) {
        if (Thread.currentThread().getName() .equals("1")) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (sharedMap.containsKey("key")) {
            String val = sharedMap.remove("key");
            if (val == null) {
                System.out.println("Value for 'key' was null");
            }
        } else {
            sharedMap.put("key", "value");
            System.out.println("=========================");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> CheckThenActExample.checkThenAct(map), "" +i).start();
        }
    }
}