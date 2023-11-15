fun main(args: Array<String>) {
    A("C")
}

class B(name: String) {
    init {
        println("object b is created!")
    }
}

class A(name: String) {

    init {
        println("init1")
    }

    constructor(age: Int) : this(name) {

    }

    val b = B("b")

    init {
        println("init2")
    }
}