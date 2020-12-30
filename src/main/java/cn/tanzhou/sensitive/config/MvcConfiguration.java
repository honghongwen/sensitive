package cn.tanzhou.sensitive.config;

import cn.tanzhou.sensitive.interceptor.SensitiveWordCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author fengwen
 * @date 2020-12-29
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Resource
    private SensitiveWordCheckInterceptor sensitiveWordCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sensitiveWordCheckInterceptor);
    }
}
