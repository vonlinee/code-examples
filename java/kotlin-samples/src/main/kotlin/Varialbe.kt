// 顶层变量
var x = 10

fun main() {
    val a: Int = 1
    val b = 2  // 自动推断为Int类型

    // This variable must either have a type annotation or be initialized
    // 如果没有初始值类型不能省略
    // val c  // 报错
    val c: Int
    c = 1

    // 可重新赋值的变量使⽤ var 关键字：
    var x = 5 // ⾃动推断出 `Int` 类型
    x += 1

    println(x)

}

// if可用作条件表达式
fun maxOf(a: Int, b: Int) = if (a > b) a else b
// fun maxOf1(a: Int, b: Int) = a > b ? a : b






