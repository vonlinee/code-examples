package org.setamv.shardingsphere.sample.dynamic.web.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 字符串到日期的转换器
 *
 * @author setamv
 * @date 20210415
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String value) {
        if(value == null) {
            return null;
        }
        value = value.trim();
        if ("".equals(value)) {
            return null;
        }
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
