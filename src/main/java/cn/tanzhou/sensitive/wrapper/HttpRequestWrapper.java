package cn.tanzhou.sensitive.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author fengwen
 * @date 2021-12-29
 * I/O复用
 */
public class HttpRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

    private final byte[] body;
    private String bodyString;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.bodyString = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        body = bodyString.getBytes(StandardCharsets.UTF_8);
    }

    public String getBodyString() {
        return this.bodyString;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream innerBAIS = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return innerBAIS.read();
            }
        };
    }
}