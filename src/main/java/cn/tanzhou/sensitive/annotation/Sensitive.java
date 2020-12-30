package cn.tanzhou.sensitive.annotation;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 2020-12-29
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {

    /**
     * 报错信息，会将敏感词汇填充入%s中，同String.format(message, 敏感词)
     */
    String message() default "%s为敏感词";

}
