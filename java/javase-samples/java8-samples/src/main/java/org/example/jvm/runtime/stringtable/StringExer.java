package org.example.jvm.runtime.stringtable;

import org.junit.jupiter.api.Test;

/**
 * 
 * @since created on 2022年7月27日
 */
public class StringExer {
	String str = new String("good");// str指向堆中good 因为是显式new，字符串常量池也有good
	int anInt = 1;
	char[] ch = { 't', 'e', 's', 't' };

	public void change(String str, char[] ch, int i) {
		System.out.println("======" + str);// good
		// String不可变性，栈上的str（String类型没有加this）变为 "test ok"，即是字符串常量池新增一个 "test
		// ok"，str指向符串常量池（
		str = "test ok";
		System.out.println("str======" + str);// str======test ok
		System.out.println("this.str======" + this.str);// this.str======good
		ch[0] = 'b';
		anInt = 2;
	}
	
	@Test
	public void test1() {
		String s1 = "abc";
		String s2 = "abc";
		System.out.println(s1 == s2); // true
		s1 += "def";
		System.out.println(s2); // abc
		System.out.println(s1); // abcdef
		System.out.println(s1 == s2); // false
	}
	
	@Test
	public void test2() {
		String s1 = "abc";
		String s2 = s1.replace('a', 'm');
		System.out.println(s1 == s2); // false
		System.out.println(s1 + " " + s2); // abc mbc
	}

	// 考察值传递，String特性
	public void change2(String str, char[] ch, int i) {
		System.out.println("======" + str);// ======good
		// 增加this明确是对象的str
		this.str = "test ok";
		System.out.println("str======" + str);// str======good
		System.out.println("this.str======" + this.str);// this.str======test ok
		ch[0] = 'b';
		anInt = 2;
	}

	public void change3(String a, char[] ch, int i) {
		System.out.println("======" + str);// ======good
		// 栈上入参为a，str为this的
		str = "test ok";
		System.out.println("======" + str);// ======test ok
		ch[0] = 'b';
		this.anInt = 2;
	}

	public static void main(String[] args) {
		/**
		 * ======good str======test ok this.str======good good best 2
		 */
		StringExer ex = new StringExer();
		ex.change(ex.str, ex.ch, ex.anInt);
		System.out.println(ex.str);// good
		System.out.println(ex.ch);// best
		System.out.println(ex.anInt);// 2

		/**
		 * ======good str======good this.str======test ok test ok best 2
		 */
		StringExer ex2 = new StringExer();
		ex2.change2(ex2.str, ex2.ch, ex2.anInt);
		System.out.println(ex2.str);// test ok
		System.out.println(ex2.ch);// best
		System.out.println(ex2.anInt);// 2
		/**
		 * ======good ======test ok test ok best 2
		 */
		StringExer ex3 = new StringExer();
		ex3.change3(ex3.str, ex3.ch, ex3.anInt);
		System.out.println(ex3.str);// test ok
		System.out.println(ex3.ch);// best
		System.out.println(ex3.anInt);// 2
	}
}
