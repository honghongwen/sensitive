package cn.tanzhou.sensitive.interceptor;

import cn.tanzhou.sensitive.annotation.Sensitive;
import cn.tanzhou.sensitive.validtor.SensitiveWordValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author fengwen
 * @date 2020-12-29
 * 敏感词拦截器
 */
@Component
public class SensitiveWordCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private SensitiveWordValidator validator;

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MethodParameter[] params = handlerMethod.getMethodParameters();
            for (MethodParameter parameter : params) {
                // 用JSR validated注解的方法需校验
                if (parameter.hasParameterAnnotation(Validated.class)) {
                    Field[] fields = parameter.getParameterType().getDeclaredFields();
                    // 按注解处理敏感词
                    resolveSensitive(convertToMapFromRequest(request), fields);
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 解析包含Sensitive注解的字段
     */
    private void resolveSensitive(Map<String, Object> paramMap, Field[] fields) {
        for (Field field : fields) {
            // 按每个字段进行校验
            if (field.getType() != String.class) {
                continue;
            }
            String value = (String) paramMap.get(field.getName());
            if (value != null) {
                Sensitive sensitive = field.getAnnotation(Sensitive.class);
                if (sensitive != null) {
                    sensitiveWordValidate(value, sensitive);
                }
            }
        }
    }

    /**
     * 校验是否包含敏感词
     */
    private void sensitiveWordValidate(String text, Sensitive sensitive) {
        if (!validator.ok(text)) {
            String message = sensitive.message();
            throw new IllegalArgumentException(String.format(message, text));
        }
    }

    /**
     * 从request中获取请求体
     */
    private Map<String, Object> convertToMapFromRequest(HttpServletRequest servletRequest) throws IOException {
        BufferedReader reader = servletRequest.getReader();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(reader, Map.class);
        logger.info("从request中获取的参数: {}", map.toString());
        return map;
    }
}
