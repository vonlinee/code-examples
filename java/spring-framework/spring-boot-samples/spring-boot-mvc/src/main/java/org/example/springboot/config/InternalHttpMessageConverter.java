package org.example.springboot.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.example.springboot.rest.MapBean;
import org.example.springboot.rest.Result;
import org.example.springboot.support.json.JsonOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @see import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
 * https://blog.csdn.net/zzuhkp/article/details/122935899
 */
public class InternalHttpMessageConverter extends AbstractHttpMessageConverter<MapBean> implements GenericHttpMessageConverter<MapBean> {

    JsonOperation<MapBean> jsonOperation;

    FastJsonHttpMessageConverter converter;

    private static final Logger log = LoggerFactory.getLogger(InternalHttpMessageConverter.class);

    public InternalHttpMessageConverter() {
        super(StandardCharsets.UTF_8, MediaType.APPLICATION_JSON);
        log.info("use http converter => {}", this);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz == MapBean.class;
    }

    @Override
    protected MapBean readInternal(Class<? extends MapBean> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(MapBean mapBean, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println(mapBean);
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return false;
    }

    @Override
    public MapBean read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        boolean flag = clazz == Result.class;
        if (flag) {
            log.info("{} => canWrite", type.getTypeName());
            return true;
        } else {
            log.info("{} => canNotWrite", type.getTypeName());
            return false;
        }
    }

    @Override
    public void write(MapBean mapBean, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println(mapBean);
    }
}
