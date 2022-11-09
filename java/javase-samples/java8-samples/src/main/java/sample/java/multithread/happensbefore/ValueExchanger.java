package sample.java.multithread.happensbefore;

public class ValueExchanger {
    private int valA;
    private int valB;
    private int valC;

    // 在 set() 方法中，方法末尾的synchronized代码块将强制所有变量在更新后同步到主内存。
    // 当线程退出同步块时，将变量值刷新到主内存。它被放在方法最后的目的是确保所有更新的变量值都被刷新到主内存中
    public void set(Values v) {
        this.valA = v.valA;
        this.valB = v.valB;
        synchronized (this) {
            this.valC = v.valC;
        }
    }

    // 在get() 方法中，同步块放在方法的开头。当调用get()的线程进入同步块时，将从主内存中重新读取所有变量。
    // 将这个同步块放在方法的开头目的是以确保所有变量在读取之前都从主内存中刷新。
    public void get(Values v) {
        synchronized (this) {
            v.valC = this.valC;
        }
        v.valB = this.valB;
        v.valA = this.valA;
    }

    public static void main(String[] args) {
        Values values;
        // 保证 values 在主内存
        synchronized (ValueExchanger.class) {
            values = new Values(1, 2, 3);
        }

        new Thread(() -> {
            ValueExchanger exchanger = new ValueExchanger();
            exchanger.set(values);
        }).start();

        new Thread(() -> {
            ValueExchanger exchanger = new ValueExchanger();
            exchanger.set(values);
        }).start();
    }
}

class Values {
    public int valC;
    public int valB;
    public int valA;

    public Values(int valC, int valB, int valA) {
        this.valC = valC;
        this.valB = valB;
        this.valA = valA;
    }
}