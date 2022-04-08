package org.setamv.shardingsphere.sample.dynamic.web.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 字符串到日期的转换器
 *
 * @author setamv
 * @date 20210415
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convert(String value) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        if ("".equals(value)) {
            return null;
        }
        return value.contains("T") ?
                LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME) :
                LocalDateTime.parse(value, DATE_TIME_FORMATTER);
    }

}
