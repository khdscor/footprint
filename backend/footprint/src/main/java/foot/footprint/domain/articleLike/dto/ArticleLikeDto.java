package foot.footprint.domain.articleLike.dto;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import lombok.Getter;

@Getter
public class ArticleLikeDto {
  private Long articleId;
  private Long memberId;
  private boolean hasILiked;

  public ArticleLikeDto(Long articleId, Long memberId, boolean hasILiked) {
    this.articleId = articleId;
    this.memberId = memberId;
    this.hasILiked = hasILiked;
  }
  public ArticleLikeDto(Long articleId, Long memberId) {
    this.articleId = articleId;
    this.memberId = memberId;
    this.hasILiked = true;
  }

  public void validateArticleIsMine(Long writerId){
    if (writerId != this.memberId) {
      throw new NotMatchMemberException("해당글에 좋아요할 권한이 없습니다.");
    }
  }
}