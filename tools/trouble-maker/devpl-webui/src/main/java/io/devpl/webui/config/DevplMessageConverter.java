package io.devpl.webui.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import io.devpl.webui.rest.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * FastJSON 2
 */
@Slf4j
public class DevplMessageConverter extends AbstractHttpMessageConverter<Object>
        implements GenericHttpMessageConverter<Object> {

    /**
     *
     */
    private Set<Class<?>> readableTypes;

    /**
     * AbstractHttpMessageConverter
     * @param clazz
     * @return
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        // 仅支持Map类型参数
        return clazz == Map.class;
    }

    /**
     * AbstractHttpMessageConverter
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        log.info("readInternal {}", clazz);
        return null;
    }

    /**
     * AbstractHttpMessageConverter
     * @param o
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        log.info("writeInternal {}", o);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            HttpHeaders headers = outputMessage.getHeaders();
            int contentLength;
            FastJsonConfig config = new FastJsonConfig();
            if (o instanceof String && JSON.isValidObject((String) o)) {
                byte[] strBytes = ((String) o).getBytes(StandardCharsets.UTF_8);
                contentLength = strBytes.length;
                baos.write(strBytes, 0, strBytes.length);
            } else {
                contentLength = JSON.writeTo(baos, o, config.getDateFormat(), config.getWriterFilters(), config.getWriterFeatures());
            }
            if (headers.getContentLength() < 0 && config.isWriteContentLength()) {
                headers.setContentLength(contentLength);
            }
            baos.writeTo(outputMessage.getBody());
        } catch (JSONException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new HttpMessageNotWritableException("I/O error while writing output message", ex);
        }
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        // 仅支持JSON格式
//        if (!MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
//            return false;
//        }
        log.info("canRead {} {} {}", type, contextClass.getSimpleName(), mediaType);
        return false;
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        log.info("read {}", type);

        return null;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Result.class);
    }

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        final HttpHeaders headers = outputMessage.getHeaders();
        addDefaultHeaders(headers, o, contentType);
        if (outputMessage instanceof StreamingHttpOutputMessage) {
            StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) outputMessage;
            streamingOutputMessage.setBody(outputStream -> writeInternal(o, new HttpOutputMessage() {
                @Override
                public OutputStream getBody() {
                    return outputStream;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return headers;
                }
            }));
        } else {
            writeInternal(o, outputMessage);
            outputMessage.getBody().flush();
        }
    }

    protected void addDefaultHeaders(HttpHeaders headers, Object t, @Nullable MediaType contentType) throws IOException {
        if (headers.getContentType() == null) {
            MediaType contentTypeToUse = contentType;
            if (contentType == null || !contentType.isConcrete()) {
                contentTypeToUse = getDefaultContentType(t);
            } else if (MediaType.APPLICATION_OCTET_STREAM.equals(contentType)) {
                MediaType mediaType = getDefaultContentType(t);
                contentTypeToUse = (mediaType != null ? mediaType : contentTypeToUse);
            }
            if (contentTypeToUse != null) {
                if (contentTypeToUse.getCharset() == null) {
                    Charset defaultCharset = getDefaultCharset();
                    if (defaultCharset != null) {
                        contentTypeToUse = new MediaType(contentTypeToUse, defaultCharset);
                    }
                }
                headers.setContentType(contentTypeToUse);
            }
        }
        if (headers.getContentLength() < 0 && !headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
            Long contentLength = getContentLength(t, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
    }
}
