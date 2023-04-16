package foot.footprint.domain.article.util;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.ArticleMethod;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateWriter {

  private final FindArticleRepository findArticleRepository;

  public void validate(ArticleMethod method, Long articleId, Long memberId) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException(method.getCode() + "하려는 게시글이 존재하지 않습니다."));
    validateMember(method, memberId, article.getMember_id());
  }

  private void validateMember(ArticleMethod method, Long memberId, Long writerId) {
    if (memberId != writerId) {
      throw new NotMatchMemberException("해당되는 글을 " + method.getCode() + "할 권한이 없습니다.");
    }
  }
}