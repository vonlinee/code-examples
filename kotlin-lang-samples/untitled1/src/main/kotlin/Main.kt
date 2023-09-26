fun main(args: Array<String>) {

    val person = Person(1230)

    val str = "$person "

    fail("Name required")
    println(1)     // 在此已知“s”已初始化

    person.say()
}

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

class Person(age: Int) {

    init {
        println("$this $age}")
    }

    fun say(): Unit {

    }

    companion object Factory {
        fun create(): Person = Person(10)
    }
}

