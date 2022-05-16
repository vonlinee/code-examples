package design.pattern.delegate.v1;

public class Printer { // the "delegator"
    RealPrinter p = new RealPrinter(); // create the delegate

    void print() {
        p.print(); // delegation
    }
}