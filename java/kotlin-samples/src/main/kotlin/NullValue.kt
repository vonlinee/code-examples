fun main() {

}

fun f(): Int? {
    return f1()  // Null can not be a value of a non-null type Int
}

fun f1(): Int? {
    return null
}

fun f3(): Int {
    val v1 = Integer.parseInt("12")
    val v2 = Integer.parseInt("24")

}