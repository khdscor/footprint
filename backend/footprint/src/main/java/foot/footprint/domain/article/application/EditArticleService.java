package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditArticleService {

  private final EditArticleRepository editArticleRepository;

  @Transactional
  public void edit(Long articleId, Long memberId, String newContent) {
    int result = editArticleRepository.editArticle(articleId, memberId, newContent);
    if (result == 0) {
      throw new NotMatchMemberException("글을 수정할 권한이 없습니다.");
    }
  }
}