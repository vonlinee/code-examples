package org.example;

import java.lang.instrument.Instrumentation;

public class AgentMainAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println(agentArgs);
    }
}
