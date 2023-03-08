package sample.java8.multithread.unsafe;

import sun.misc.VM;

public class TestVM {


    //    sun.misc.VM
    @SuppressWarnings("restriction")
    public static void testVm() {
        //sun.nio.MaxDirectMemorySize
        System.out.println(VM.maxDirectMemory() / 1024 / 1024 / 1024);
        boolean allowArraySyntax = VM.allowArraySyntax();
        VM.booted();
        VM.initializeOSEnvironment();
        Thread.State state = VM.toThreadState(1);
        ClassLoader classLoader = VM.latestUserDefinedLoader(); //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(classLoader);

        System.out.println(VM.isBooted());
        System.out.println(VM.isDirectMemoryPageAligned());

        try {
            VM.awaitBooted();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VM.addFinalRefCount(10);
    }
}
