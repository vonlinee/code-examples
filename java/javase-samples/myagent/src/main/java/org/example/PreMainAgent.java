package org.example;

import java.lang.instrument.Instrumentation;

public class PreMainAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        // 这里可以插入代码以修改字节码
        System.out.println(agentArgs);
    }
}
