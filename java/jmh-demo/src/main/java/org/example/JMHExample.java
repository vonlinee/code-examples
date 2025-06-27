package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class JMHExample {

    private int[] numbers;

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(JMHExample.class.getSimpleName()) // 指定测试的类
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }

    @Setup(Level.Trial)
    public void setup() {
        numbers = new int[1000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }
    }

    @Benchmark
    public int sumArray() {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    @Benchmark
    public int sumArrayParallel() {
        return Arrays.stream(numbers).parallel().sum();
    }
}