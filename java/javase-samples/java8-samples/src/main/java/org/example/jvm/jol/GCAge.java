package org.example.jvm.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Contended;

public class GCAge {

    volatile Object consumer;

    public void simulationMinorGC() {
        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        ClassLayout layout = ClassLayout.parseInstance(instance);
        for (int i = 0; i < 10_000; i++) {
            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                System.out.println(layout.toPrintable());
            }
            for (int j = 0; j < 10_000; j++) {
                consumer = new Object();
            }
            lastAddr = currentAddr;
        }

        Class<Contended> contendedClass = Contended.class;
    }

    public static void main(String[] args) {
        GCAge test = new GCAge();
        test.simulationMinorGC();
    }
}
