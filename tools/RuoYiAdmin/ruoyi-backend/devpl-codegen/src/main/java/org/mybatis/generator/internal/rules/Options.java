package org.mybatis.generator.internal.rules;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Options {

    Map<String, Boolean> optionsRegistry = new ConcurrentHashMap<>();
}
