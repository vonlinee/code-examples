package org.example;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class Main {

    static {
        System.out.println("static init");
    }


    public static void main(String[] args) throws IOException, AttachNotSupportedException, ClassNotFoundException {
//        VirtualMachine vm = VirtualMachine.attach("2424");
//
//        String agent = "D:\\Develop\\Code\\code-samples\\java\\agentdemo\\src\\main\\resources\\mybatisx-agent.jar";
//
//        try {
//            vm.loadAgent(agent, "8090");
//        } catch (AgentLoadException | AgentInitializationException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println(Main.class.getClassLoader());

        ClassLoader extLoader = Main.class.getClassLoader().getParent();

        ClassLoader bootClassLoader = extLoader.getParent();

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        System.out.println(contextClassLoader);

//
//        try {
//            Thread.sleep(40000);
//        } catch (InterruptedException e) {
//
//        } finally {
//            vm.detach();
//        }
    }
}