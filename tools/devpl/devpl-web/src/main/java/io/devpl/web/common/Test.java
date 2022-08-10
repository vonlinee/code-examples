package io.devpl.web.common;

public class Test {


    public static void main(String[] args) {
        try {
            throwException();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void throwException() {
        try {
            int i = 1 / 0;
        } catch (ArithmeticException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
}
