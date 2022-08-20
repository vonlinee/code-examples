package groovy.helloword;

/**
 * @author vonline
 * @since 2022-08-18 23:26
 */
class Closure {

    static void main(String[] args) {
        def i = 0
        def c1 = { i++ }
        def c2 = { -> i-- }

        def cl = { String x, int y -> println "hey ${x} the value is ${y}" }
    }

}
