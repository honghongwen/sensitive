package cn.tanzhou.sensitive.anno;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 2020-12-29
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {

    /**
     * 报错信息，会将敏感词汇填充入{}中，同String.format
     */
    String message() default "{}为敏感词";



}
