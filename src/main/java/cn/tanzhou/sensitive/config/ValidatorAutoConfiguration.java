package cn.tanzhou.sensitive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author fengwen
 * @date 2020-12-29
 */
@Configuration
public class ValidatorAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private SensitiveArgumentInterceptor sensitiveArgumentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sensitiveArgumentInterceptor);
    }
}
