fun main() {
    println(sum(1, 2))
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum1(a: Int, b: Int) = a + b

// 函数返回无意义的值
fun printSum(a: Int, b: Int): Unit {
    println(a + b)
}

// Unit返回类型可省略
fun printSum1(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}