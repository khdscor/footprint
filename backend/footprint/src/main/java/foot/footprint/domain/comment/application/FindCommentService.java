package foot.footprint.domain.comment.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ValidateIsMine;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindCommentService {

  private final FindArticleRepository findArticleRepository;

  private final FindCommentRepository findCommentRepository;

  private final ArticleGroupRepository articleGroupRepository;

  @Transactional
  public List<CommentResponse> findComments(Long articleId, CustomUserDetails userDetails) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException("해당하는 게시글이 존재하지 않습니다."));
    if (article.isPublic_map() == true) {
      return findCommentsByPublic(articleId);
    }
    if (article.isPrivate_map() == true) {
      return findCommentsByPrivate(article, userDetails);
    }
    return findCommentsByGrouped(article, userDetails);
  }

  private List<CommentResponse> findCommentsByPublic(Long articleId) {
    return findCommentRepository.findAllByArticleId(articleId);
  }

  private List<CommentResponse> findCommentsByPrivate(Article article, CustomUserDetails userDetails) {
    if (userDetails == null) {
      throw new NotAuthorizedOrExistException("해당 글에 접근할 권한이 없습니다.");
    }
    ValidateIsMine.validateArticleIsMine(article.getMember_id(), userDetails.getId());
    return findCommentRepository.findAllByArticleId(article.getId());
  }

  private List<CommentResponse> findCommentsByGrouped(Article article, CustomUserDetails userDetails) {
    if (userDetails == null) {
      throw new NotAuthorizedOrExistException("해당 글에 접근할 권한이 없습니다.");
    }
    ValidateIsMine.validateInMyGroup(article.getId(), userDetails.getId(), articleGroupRepository);
    return findCommentRepository.findAllByArticleId(article.getId());
  }
}