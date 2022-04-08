package org.setamv.shardingsphere.sample.dynamic.web.converters;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串到日期的转换器
 *
 * @author setamv
 * @date 20210415
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final String NUMBER_PATTERN = "^\\d+$";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Date convert(String value) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        if ("".equals(value)) {
            return null;
        }
        // Unix时间戳格式的时间
        if (value.matches(NUMBER_PATTERN)) {
            return new Date(Long.parseLong(value));
        }
        SimpleDateFormat formatter = value.contains(":") ? new SimpleDateFormat(DATE_TIME_FORMAT) : new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("parser %s to Date fail", value));
        }
    }
}
