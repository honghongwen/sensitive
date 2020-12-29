package cn.tanzhou.sensitive.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author fengwen
 * @date 2021-12-29
 * I/O复用
 */
@Component
public class SensitiveRequestReplacedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException {
        try {
            SafeHttpServletRequestWrapper requestWrapper = new SafeHttpServletRequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(requestWrapper, servletResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}