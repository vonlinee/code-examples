package io.maker.base.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 数学工具类
 */
public final class MathUtils {

    //随机算法
    public static final String RANDOM_ALGORITHM_1 = "SHA1PRNG";
    public static final String RANDOM_ALGORITHM_2 = "SUN";

    //并发安全
    private static final SecureRandom srandom = new SecureRandom();

    /**
     * 防止并发问题
     */
    private static final class RandomNumberGeneratorHolder {
        static final Random randomNumberGenerator = new Random();
    }

    public static int randomInt() {
        return srandom.nextInt();
    }

    public static int randomInt(int min, int max) {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextInt(min);
    }


    public static int randomInt(boolean positive, boolean mayBeZero) {
        if (positive) {

        }
        return srandom.nextInt();
    }


    public static void main(String[] args) {
        System.out.println(randomInt());
    }
}
