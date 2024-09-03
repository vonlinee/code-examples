fun main() {
    var sum = 0
    val ints = listOf(1, 2, 4)
    ints.filter { it > 0 }.forEach {
        sum += it
    }
    print(sum)
}
