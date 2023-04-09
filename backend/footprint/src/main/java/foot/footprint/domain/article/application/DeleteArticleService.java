package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.DeleteArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteArticleService {

  private final DeleteArticleRepository deleteArticleRepository;

  private final FindArticleRepository findArticleRepository;

  @Transactional
  public void delete(Long articleId, Long memberId) {
    validate(articleId, memberId);
    deleteArticleRepository.deleteById(articleId);
  }

  private void validate(Long articleId, Long memberId) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException("삭제하려는 게시글이 존재하지 않습니다."));
    validateMember(memberId, article.getMember_id());
  }

  private void validateMember(Long memberId, Long writerId) {
    if (memberId != writerId) {
      throw new NotMatchMemberException("해당되는 글을 삭제할 권한이 없습니다.");
    }
  }
}