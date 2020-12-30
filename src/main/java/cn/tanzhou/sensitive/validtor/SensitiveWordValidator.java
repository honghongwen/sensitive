package cn.tanzhou.sensitive.validtor;

import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengwen
 * @date 2021-12-29
 * 基于DFA算法的敏感词校验器
 */
public class SensitiveWordValidator implements InitializingBean {

    private static Map<String, Object> sensitiveCache = new HashMap<>();

    public static boolean ok(String text) {
        boolean check = sensitiveCache.get(text) != null;
        return check;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("sensitive.txt");
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sensitiveCache.put(line, line);
        }
    }
}
