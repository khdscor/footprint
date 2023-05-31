package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.DeleteArticleRepository;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteArticleService {

  private final DeleteArticleRepository deleteArticleRepository;

  @Transactional
  public void delete(Long articleId, Long memberId) {
    int result = deleteArticleRepository.deleteById(articleId, memberId);
    if (result == 0) {
      throw new NotMatchMemberException("글을 삭제할 권한이 없습니다.");
    }
  }
}