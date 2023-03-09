package org.example.test;

import org.example.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Request callback implementation that prepares(准备) the request's accept headers.
 *
 * @see RestTemplate.HttpEntityRequestCallback
 */
public class CustomRequestCallback implements RequestCallback {

    Logger logger = LoggerFactory.getLogger(CustomRequestCallback.class);

    RestTemplate template;

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    private HttpEntity<?> requestEntity;

    @Nullable
    private final Type responseType;

    public CustomRequestCallback(@Nullable Object requestBody) {
        this(requestBody, null);
    }

    public CustomRequestCallback(@Nullable Object requestBody, @Nullable Type responseType) {
        ParameterizedType parameterizedType = new MyParameterizedType(Result.class, new Type[]{responseType}, Result.class);
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
        // Result<List<Employee>>
        this.responseType = parameterizedType.getRawType();

        logger.info("responseType {}", responseType);

        if (requestBody instanceof HttpEntity) {
            this.requestEntity = (HttpEntity<?>) requestBody;
        } else if (requestBody != null) {
            this.requestEntity = new HttpEntity<>(requestBody);
        } else {
            this.requestEntity = HttpEntity.EMPTY;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doWithRequest(ClientHttpRequest httpRequest) throws IOException {
        doWithRequestInternal(httpRequest);
        Object requestBody = this.requestEntity.getBody();
        if (requestBody == null) {
            HttpHeaders httpHeaders = httpRequest.getHeaders();
            HttpHeaders requestHeaders = this.requestEntity.getHeaders();
            if (!requestHeaders.isEmpty()) {
                requestHeaders.forEach((key, values) -> httpHeaders.put(key, new ArrayList<>(values)));
            }
            if (httpHeaders.getContentLength() < 0) {
                httpHeaders.setContentLength(0L);
            }
        } else {
            Class<?> requestBodyClass = requestBody.getClass();
            Type requestBodyType = (this.requestEntity instanceof RequestEntity ? ((RequestEntity<?>) this.requestEntity).getType() : requestBodyClass);
            HttpHeaders httpHeaders = httpRequest.getHeaders();
            HttpHeaders requestHeaders = this.requestEntity.getHeaders();
            MediaType requestContentType = requestHeaders.getContentType();
            for (HttpMessageConverter<?> messageConverter : template.getMessageConverters()) {
                if (messageConverter instanceof GenericHttpMessageConverter) {
                    GenericHttpMessageConverter<Object> genericConverter = (GenericHttpMessageConverter<Object>) messageConverter;
                    if (genericConverter.canWrite(requestBodyType, requestBodyClass, requestContentType)) {
                        if (!requestHeaders.isEmpty()) {
                            requestHeaders.forEach((key, values) -> httpHeaders.put(key, new ArrayList<>(values)));
                        }
                        logBody(requestBody, requestContentType, genericConverter);
                        genericConverter.write(requestBody, requestBodyType, requestContentType, httpRequest);
                        return;
                    }
                } else if (messageConverter.canWrite(requestBodyClass, requestContentType)) {
                    if (!requestHeaders.isEmpty()) {
                        requestHeaders.forEach((key, values) -> httpHeaders.put(key, new ArrayList<>(values)));
                    }
                    logBody(requestBody, requestContentType, messageConverter);
                    ((HttpMessageConverter<Object>) messageConverter).write(requestBody, requestContentType, httpRequest);
                    return;
                }
            }
            String message = "No HttpMessageConverter for " + requestBodyClass.getName();
            if (requestContentType != null) {
                message += " and content type \"" + requestContentType + "\"";
            }
            throw new RestClientException(message);
        }
    }

    private void logBody(Object body, @Nullable MediaType mediaType, HttpMessageConverter<?> converter) {
        if (logger.isDebugEnabled()) {
            if (mediaType != null) {
                logger.debug("Writing [" + body + "] as \"" + mediaType + "\"");
            } else {
                logger.debug("Writing [" + body + "] with " + converter.getClass().getName());
            }
        }
    }

    public void doWithRequestInternal(@NonNull ClientHttpRequest request) throws IOException {
        if (this.responseType != null) {
            List<MediaType> allSupportedMediaTypes = template.getMessageConverters()
                    .stream()
                    .filter(converter -> canReadResponse(this.responseType, converter))
                    .flatMap(this::getSupportedMediaTypes)
                    .distinct()
                    .sorted(MediaType.SPECIFICITY_COMPARATOR)
                    .collect(Collectors.toList());
            if (logger.isDebugEnabled()) {
                logger.debug("Accept=" + allSupportedMediaTypes);
            }
            request.getHeaders().setAccept(allSupportedMediaTypes);
        }
    }

    private boolean canReadResponse(Type responseType, HttpMessageConverter<?> converter) {
        Class<?> responseClass = (responseType instanceof Class ? (Class<?>) responseType : null);
        if (responseClass != null) {
            return converter.canRead(responseClass, null);
        } else if (converter instanceof GenericHttpMessageConverter) {
            GenericHttpMessageConverter<?> genericConverter = (GenericHttpMessageConverter<?>) converter;
            return genericConverter.canRead(responseType, null, null);
        }
        return false;
    }

    private Stream<MediaType> getSupportedMediaTypes(HttpMessageConverter<?> messageConverter) {
        return messageConverter.getSupportedMediaTypes().stream().map(mediaType -> {
            if (mediaType.getCharset() != null) {
                return new MediaType(mediaType.getType(), mediaType.getSubtype());
            }
            return mediaType;
        });
    }
}
