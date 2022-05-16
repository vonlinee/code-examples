package design.pattern.delegate.v1;

/**
 * 类模拟打印机Printer拥有针式打印机RealPrinter的实例，Printer拥有的方法print()将处理转交给RealPrinter的方法print()
 */
public class Main {
    // to the outside world it looks like Printer actually prints.
    public static void main(String[] args) {
        Printer printer = new Printer();
        printer.print();
    }
}