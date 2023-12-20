package foot.footprint.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageDto;
import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.global.error.exception.WrongAccessRedisException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ObjectSerializer {

    private final RedisTemplate<String, String> redisTemplate;

    public <T> void saveData(String key, T data, int seconds) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("예외 메세지: " + e.getMessage());
            throw new WrongAccessRedisException("캐시에 데이터를 저장하는데 실패하였습니다.");
        }
    }

    public <T> Optional<T> getData(String key, Class<T> classType) {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return Optional.empty();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(value, classType));
        } catch (Exception e) {
            log.info("예외 메세지: " + e.getMessage());
            throw new WrongAccessRedisException("캐시에서 데이터를 가져오는데 실패하였습니다.");
        }
    }

    public<T> void updateArticleData(String key, ArticleUpdatePart part, T data) {
        Optional<ArticlePageDto> cache = getData(key, ArticlePageDto.class);
        if (cache.isPresent()) {
            part.apply(cache.get(), data);
            saveData(key, cache.get(), 10);
        }
    }
}