package io.devpl.spring.web.mvc;

import com.google.gson.Gson;
import io.devpl.sdk.rest.RestfulResultTemplate;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 只支持JSON格式的数据传输
 */
public class RestHttpMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    @Override
    protected boolean supports(Class<?> clazz) {
        return RestfulResultTemplate.class.isAssignableFrom(clazz);
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return true;
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return false;
    }

    /**
     * 支持的媒体类型
     * @return 仅支持文本或者JSON格式
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        RestfulResultTemplate result = (RestfulResultTemplate) o;
        Gson gson = new Gson();
        String str = gson.toJson(result);
        outputMessage.getBody().write(str.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }
}
