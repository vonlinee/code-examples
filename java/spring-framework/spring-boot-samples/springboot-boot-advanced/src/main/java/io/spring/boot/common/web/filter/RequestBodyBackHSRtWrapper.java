package io.spring.boot.common.web.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

public class RequestBodyBackHSRtWrapper extends HttpServletRequestWrapper {
    private final Logger log = LoggerFactory.getLogger(RequestBodyBackHSRtWrapper.class);
    private byte[] requestBody;

    public RequestBodyBackHSRtWrapper(HttpServletRequest request) {
        super(request);
        try {
            this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException var3) {
            this.log.error("bodycp filter read body error", var3);
        }

    }

    public ServletInputStream getInputStream() {
        if (this.requestBody == null) {
            this.requestBody = new byte[0];
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.requestBody);
        return null;
    }
}