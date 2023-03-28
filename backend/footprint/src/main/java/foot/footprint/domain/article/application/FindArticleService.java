package foot.footprint.domain.article.application;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.LocationRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindArticleService {
    private final FindArticleRepository findArticleRepository;

    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findPublicMapArticles(
            LocationRange locationRange) {
        Long userId = null;
        return toResponses(findArticles(userId, locationRange));
    }
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findPrivateMapArticles(
            Long userId, LocationRange locationRange) {
        return toResponses(findArticles(userId, locationRange));
    }

    private List<Article> findArticles(Long userId, LocationRange locationRange) {
        return findArticleRepository.findArticles(userId,
                locationRange.getUpperLatitude(), locationRange.getLowerLatitude(),
                locationRange.getUpperLongitude(), locationRange.getLowerLongitude());
    }

    private List<ArticleMapResponse> toResponses(List<Article> articles) {
        if (Objects.isNull(articles)) {
            return Collections.emptyList();
        }
        return articles.stream()
                .map(article -> new ArticleMapResponse(
                        article.getId(),
                        article.getLatitude(),
                        article.getLongitude(),
                        article.getTitle()))
                .collect(Collectors.toUnmodifiableList());
    }
}
