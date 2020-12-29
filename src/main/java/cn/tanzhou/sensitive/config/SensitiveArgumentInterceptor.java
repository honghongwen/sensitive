package cn.tanzhou.sensitive.config;

import cn.tanzhou.sensitive.valid.SensitiveValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Objects;


/**
 * @author fengwen
 * @date 2020-12-29
 */
@Component
public class SensitiveArgumentInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveArgumentInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MethodParameter[] params = handlerMethod.getMethodParameters();
            for (MethodParameter parameter : params) {
                // 用JSR validated注解的方法需校验
                if (parameter.hasParameterAnnotation(Validated.class)) {
                    // 读取前端传过来的参数
                    Map<String, Object> paramMap = convertToMapFromRequest(request);
                    Field[] fields = parameter.getParameterType().getDeclaredFields();
                    // 按注解处理敏感词
                    resolveSensitive(paramMap, fields);

                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void resolveSensitive(Map<String, Object> paramMap, Field[] fields) {
        for (Field field : fields) {
            // 按每个字段进行校验
            if (field.getType() != String.class) {
                continue;
            }
            Object obj = paramMap.get(field.getName());
            // TODO 看是否有指定注解
            if (obj != null) {
                sensitiveWordValidate(String.valueOf(obj));
            }
        }
    }

    private void sensitiveWordValidate(String text) {
        // TODO 具体的敏感词校验规则
        if (!SensitiveValidator.check(text)) {
            throw new IllegalArgumentException("对不起，你触发了高风险敏感词");
        }
    }

    private Map<String, Object> convertToMapFromRequest(HttpServletRequest servletRequest) throws IOException {
        BufferedReader reader = servletRequest.getReader();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(reader, Map.class);
        logger.info("转换为map后: {}", map.toString());
        return map;
    }
}
