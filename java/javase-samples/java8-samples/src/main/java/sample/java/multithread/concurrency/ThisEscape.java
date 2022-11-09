package sample.java.multithread.concurrency;

/**
 * This溢出问题
 */
public class ThisEscape {

    private M m;

    public ThisEscape() {
    	new Thread(() -> {
    		doSomething(10);
    	}).start();
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    private void doSomething(int i) {
        if (i > 5) {
            System.out.println("Race condition detected");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                new ThisEscape().m.method();
            }).start();
        }
    }
}

interface M {
    void method();
}

//    ThisEscape编译后会有两个class 文件。一个是ThisEscape.class, 另个一个会是 ThisEscape$1.class 给这个匿名类

// 编译后的结果
//public class ThisEscape {
//    private M m = new M() {
//        public void method() {
//            System.out.println(this);

//              java内部类调用外部class的标准方式 - 外部类名.this.方法名
//            ThisEscape.this.doSomething(10);
//        }
//    };
//
//    public ThisEscape() {
//    }
//
//    private void doSomething(int i) {
//        if (i > 5) {
//            System.out.println("Race condition detected");
//        }
//
//    }
//
//    public static void main(String[] args) {
//        (new ThisEscape()).m.method();
//    }
//}