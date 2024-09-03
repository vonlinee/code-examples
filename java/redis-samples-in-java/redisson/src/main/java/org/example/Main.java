package org.example;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;

public class Main {
    public static void main(String[] args) {


        RedissonClient client = Redisson.create();

        RBloomFilter<String> filter = client.getBloomFilter("code_key");
        boolean res = filter.tryInit(1000, 0.03);

        filter.add("111");

    }

    private int calculateOptimalSize(int n, double p) {
        return (int) -(n * Math.log(p) / Math.pow(Math.log(2), 2));
    }

    private int calculateOptimalHashFunctions(int n, double m) {
        return (int) Math.ceil((m / n) * Math.log(2));
    }
}