package io.devpl.sdk.util;

import java.util.Map;
import java.util.function.Predicate;

/**
 * 预先缓存一些常用的断言
 */
public class Predicates {

    public static final Predicate<Map<?, ?>> EMPTY_MAP = map -> map == null || map.isEmpty();
}
