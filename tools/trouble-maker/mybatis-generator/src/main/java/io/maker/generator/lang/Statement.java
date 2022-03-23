package io.maker.generator.lang;

/**
 * 语句则只干活，并不返回。一个语句由1个或多个表达式组成
 * 有的编程语言只有表达式，没有语句
 * 那些不需要返回值的东西，比如循环，赋值，定义
 */
public abstract class Statement {
    //有些语言没有结束标记
    public static final String END = ";";
    private static final String L = "";

}
