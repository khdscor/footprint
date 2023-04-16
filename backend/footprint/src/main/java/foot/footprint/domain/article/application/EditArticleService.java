package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.domain.ArticleMethod;
import foot.footprint.domain.article.util.ValidateWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditArticleService {

  private final EditArticleRepository editArticleRepository;

  private final ValidateWriter validateWriter;

  @Transactional
  public void edit(Long articleId, Long memberId, String newContent) {
    validateWriter.validate(ArticleMethod.Edit, articleId, memberId);
    editArticleRepository.editArticle(articleId, newContent);
  }
}