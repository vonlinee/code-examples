package org.setamv.shardingsphere.starter.config.converters;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";

    @Override
    public Date convert(String value) {
        if(value == null) {
            return null;
        }
        value = value.trim();
        if ("".equals(value)) {
            return null;
        }
        if (value.matches("^\\d+$")) {
            return new Date(Long.valueOf(value));
        }
        SimpleDateFormat formatter = value.contains(":") ? new SimpleDateFormat(dateFormat) : new SimpleDateFormat(shortDateFormat);
        try {
            return formatter.parse(value);
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", value));
        }
    }
}
