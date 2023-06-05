package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.domain.CommentLike;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ValidateIsMine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

  private final FindArticleRepository findArticleRepository;

  private final CommentLikeRepository commentLikeRepository;

  private final ArticleGroupRepository articleGroupRepository;

  @Transactional
  public void changeMyLike(Long commentId, Long articleId, Boolean hasILiked, Long memberId) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException("해당하는 게시글이 존재하지 않습니다."));
    if (article.isPublic_map()) {
      changeLikeByPublic(commentId, memberId, hasILiked);
      return;
    }
    if (article.isPrivate_map()) {
      changeLikeByPrivate(article, commentId, memberId, hasILiked);
      return;
    }
    changeLikeByGrouped(articleId, commentId, memberId, hasILiked);
  }

  private void changeLikeByPublic(Long commentId, Long memberId, boolean hasILiked) {
    changeLike(commentId, memberId, hasILiked);
  }

  private void changeLikeByPrivate(Article article, Long commentId, Long memberId,
      boolean hasILiked) {
    ValidateIsMine.validateArticleIsMine(article.getMember_id(), memberId);
    changeLike(commentId, memberId, hasILiked);
  }

  private void changeLikeByGrouped(Long articleId, Long commentId, Long memberId,
      boolean hasILiked) {
    ValidateIsMine.validateInMyGroup(articleId, memberId, articleGroupRepository);
    changeLike(commentId, memberId, hasILiked);
  }

  private void changeLike(Long commentId, Long memberId, boolean hasILiked) {
    if (hasILiked) {
      deleteLike(commentId, memberId);
      return;
    }
    commentLikeRepository.saveCommentLike(new CommentLike(commentId, memberId));
  }

  private void deleteLike(Long commentId, Long memberId) {
    int deleted = commentLikeRepository.deleteCommentLike(commentId, memberId);
    if (deleted == 0) {
      throw new NotExistsException("이미 좋아요를 취소하였거나 누르지 않았습니다.");
    }
  }
}