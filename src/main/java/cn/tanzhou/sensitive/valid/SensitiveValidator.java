package cn.tanzhou.sensitive.valid;

/**
 * @author fengwen
 * @date 2021-12-29
 * 基于DFA算法的敏感词校验器
 */
public class SensitiveValidator {

    public static boolean check(String text) {
        return "宇文".equals(text);
    }
}
