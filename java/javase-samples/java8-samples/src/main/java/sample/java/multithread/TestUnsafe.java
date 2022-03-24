package sample.java.multithread;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;

import sun.misc.Unsafe;
import sun.misc.VM;
import sun.reflect.Reflection;

public class TestUnsafe {

    public static void main(String[] args) throws NoSuchAlgorithmException {
    	
//        doUnsafe(unsafe -> {
////            unsafe.freeMemory(2 * 1024 * 1024);
//
//            System.out.println(unsafe.addressSize());
//
//            long l = unsafe.allocateMemory(200);
//            System.out.println(l / 1024 / 1024);
//        });
    	
    }

//    public static void doUnsafe(Consumer<Unsafe> unsafeConsumer) {
//        Unsafe unsafe = getUnsafeByReflection();
//        if (unsafe == null) {
//            System.out.println("unsafe is null!");
//        }
//        unsafeConsumer.accept(unsafe);
//    }

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

    @SuppressWarnings("restriction")
	public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({
			"restriction", "unused"
	})
	private static Unsafe getUnsafe1() {
        Class<?> var0 = Reflection.getCallerClass();
        if (!VM.isSystemDomainLoader(var0.getClassLoader())) {
            throw new SecurityException("Unsafe");
        } else {
//            return theUnsafe;  //返回单例Unsafe实例
            return null;
        }
    }
}