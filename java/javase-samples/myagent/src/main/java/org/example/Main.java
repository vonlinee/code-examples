package org.example;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        // 获取当前 JVM 的运行时信息
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // name 格式为 "<pid>@<hostname>"
        String pid = name.split("@")[0];
        System.out.println("current jvm pid is: " + pid);

        TimeUnit.SECONDS.sleep(2);

        System.out.println("try to load agent " + args[0] + args[1]);
        attachAgentToTargetJVM(pid, args[0], args[1]);
        System.out.println("load " + args[0] + " successfully.");
    }

    public static void attachAgentToTargetJVM(String targetVmPid, String agent, String agentArgs) throws Exception {
        // 获取运行的JVM实例
        List<VirtualMachineDescriptor> virtualMachineDescriptors = VirtualMachine.list();
        VirtualMachineDescriptor targetVM = null;
        for (VirtualMachineDescriptor descriptor : virtualMachineDescriptors) {
            if (descriptor.id().equals(targetVmPid)) {
                targetVM = descriptor;
                break;
            }
        }
        if (targetVM == null) {
            throw new IllegalArgumentException("could not find the target jvm by process id:" +
                    targetVmPid);
        }
        VirtualMachine virtualMachine = null;
        try {
            virtualMachine = VirtualMachine.attach(targetVM);
            virtualMachine.loadAgent(agent, agentArgs);
        } catch (Exception e) {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
    }
}
