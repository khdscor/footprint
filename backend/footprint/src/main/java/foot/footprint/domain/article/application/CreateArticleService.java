package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.CreateArticleRequest;
import foot.footprint.domain.article.exception.NotIncludedMapException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateArticleService {

  private final CreateArticleRepository articleRepository;

  @Transactional
  public Long create(CreateArticleRequest request, Long memberId) {
    Article article = Article.createArticle(request, memberId);
    validateMapType(request);
    articleRepository.saveArticle(article);

    return article.getId();
  }

  void validateMapType(CreateArticleRequest request) {
    List<Long> groups = request.getGroupIdsToBeIncluded();

    if (!request.isPublicMap() && !request.isPrivateMap()
        && (Objects.isNull(groups) || groups.isEmpty())) {
      throw new NotIncludedMapException("전체지도, 그룹지도, 개인지도 중 하나는 포함해야합니다.");
    }
  }
}