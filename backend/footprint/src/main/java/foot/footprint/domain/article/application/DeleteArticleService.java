package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.DeleteArticleRepository;
import foot.footprint.domain.article.domain.ArticleMethod;
import foot.footprint.domain.article.util.ValidateWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteArticleService {

  private final DeleteArticleRepository deleteArticleRepository;

  private final ValidateWriter validateWriter;

  @Transactional
  public void delete(Long articleId, Long memberId) {
    validateWriter.validate(ArticleMethod.Delete, articleId, memberId);
    deleteArticleRepository.deleteById(articleId);
  }
}