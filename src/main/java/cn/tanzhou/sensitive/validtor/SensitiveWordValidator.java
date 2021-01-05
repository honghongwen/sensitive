package cn.tanzhou.sensitive.validtor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fengwen
 * @date 2021-12-29
 * 基于DFA算法的敏感词校验器
 */
@Component
public class SensitiveWordValidator {

    private final static Logger logger = LoggerFactory.getLogger(SensitiveWordValidator.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 首字缓存
     */
    private static final String TOP_CACHE = "sensitive:word:top";

    /**
     * 将用首字做key构建缓存
     */
    private static final String SECOND_CACHE = "sensitive:word:second:";

    public boolean ok(String text) {
        List<String> sensitives = new ArrayList<>();
        try {
//            sensitives = findAllSensitiveWords();
            // 获取命中首字缓存的字符
            List<Integer> wordInTopCache = findWordInTopCache(text);
            for (Integer index : wordInTopCache) {
                // 继续后续字符的校验
                validateNextWords(index, text);
            }
        } catch (Exception e) {
            logger.error("获取敏感词失败", e);
        }
        return !sensitives.contains(text);
    }

    /**
     * 使用二级缓存校验信息
     */
    private void validateNextWords(Integer index, String text) {

    }

    /**
     * 查找命中了首字缓存的字符下标
     *
     * @param text
     * @return
     */
    private List<Integer> findWordInTopCache(String text) {
        List<Integer> indexList = new ArrayList<>();
        char[] chars = text.toCharArray();
        // 首字缓存
        List<Object> topCache = findTopCache();

        for (int i = 0; i < chars.length; i++) {
            if (topCache.contains(chars[i])) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    /**
     * 获取首字缓存
     */
    private List<Object> findTopCache() {
        return redisTemplate.opsForHash().values(TOP_CACHE);
    }

    /**
     * 获取敏感词
     *
     * @return
     * @throws IOException
     */
    private List<String> findAllSensitiveWords() throws IOException {
        List<String> list = new ArrayList<>();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("sensitive.txt");
        Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

}
