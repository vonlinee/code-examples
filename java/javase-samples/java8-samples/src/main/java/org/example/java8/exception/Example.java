package org.example.java8.exception;

public class Example {
   public static void main(String[] args) {
       f1();
   }

   static void f1() {
       f2();
   }

   static void f2() {
       f3();
   }

   static void f3() {
       f4();
   }

   static void f4() {
       Thread.dumpStack();
   }
}