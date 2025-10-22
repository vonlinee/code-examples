package coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val channel = Channel<Int>()

    // 生产者
    launch {
        for (x in 1..5) {
            channel.send(x * x)
            delay(100L)
        }
        channel.close() // 关闭通道
    }

    // 消费者
    launch {
        for (y in channel) {
            println("Received: $y")
        }
    }
}