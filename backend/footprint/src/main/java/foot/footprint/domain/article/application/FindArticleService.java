package foot.footprint.domain.article.application;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.LocationRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindArticleService {

  private final FindArticleRepository findArticleRepository;

  @Transactional(readOnly = true)
  public List<ArticleMapResponse> findPublicMapArticles(
      LocationRange locationRange) {
    Long userId = null;
    return ArticleMapResponse.toResponses(findArticles(userId, locationRange));
  }

  @Transactional(readOnly = true)
  public List<ArticleMapResponse> findPrivateMapArticles(
      Long userId, LocationRange locationRange) {
    return ArticleMapResponse.toResponses(findArticles(userId, locationRange));
  }

  private List<Article> findArticles(Long userId, LocationRange locationRange) {
    return findArticleRepository.findArticles(userId, locationRange);
  }
}
