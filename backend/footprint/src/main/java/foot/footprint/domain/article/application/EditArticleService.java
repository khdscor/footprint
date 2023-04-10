package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditArticleService {

  private final EditArticleRepository editArticleRepository;

  private final FindArticleRepository findArticleRepository;

  @Transactional
  public void edit(Long articleId, Long memberId, String newContent) {
    validate(articleId, memberId);
    editArticleRepository.editArticle(articleId, newContent);
  }

  private void validate(Long articleId, Long memberId) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException("수정하려는 게시글이 존재하지 않습니다."));
    validateMember(memberId, article.getMember_id());
  }

  private void validateMember(Long memberId, Long writerId) {
    if (memberId != writerId) {
      throw new NotMatchMemberException("해당되는 글을 수정할 권한이 없습니다.");
    }
  }
}