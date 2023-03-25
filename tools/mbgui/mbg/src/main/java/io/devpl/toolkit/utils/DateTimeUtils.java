package io.devpl.toolkit.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DateTimeUtils {

    public static String nowDateTime() {
        return DateUtil.format(new Date(), "yyyyMMddHHmmss");
    }
}
