package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
public class JMHExample_Foreach {

    final int count = 100000000;

    @Benchmark
    public void inner() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Object obj = new Object();
            list.add(obj);
        }
    }

    @Benchmark
    public void outer() {
        List<Object> list = new ArrayList<>();
        Object obj;
        for (int i = 0; i < count; i++) {
            obj = new Object();
            list.add(obj);
        }
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(JMHExample_Foreach.class.getSimpleName()) // 指定测试的类
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}