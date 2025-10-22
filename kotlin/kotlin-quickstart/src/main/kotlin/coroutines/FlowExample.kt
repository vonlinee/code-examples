package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FlowExample {
    fun simpleFlow(): Flow<Int> = flow {
        // 流构建器
        for (i in 1..3) {
            delay(100L)
            emit(i) // 发射值
        }
    }

    suspend fun collectFlow() {
        simpleFlow()
            .map { it * it } // 转换
            .filter { it % 2 != 0 } // 过滤
            .collect { value -> // 收集
                println("Collected: $value")
            }
    }

    // 状态流
    private val _state = MutableStateFlow(0)
    val state: StateFlow<Int> = _state.asStateFlow()

    // 共享流
    private val _events = MutableSharedFlow<Int>()
    val events: SharedFlow<Int> = _events.asSharedFlow()
}

