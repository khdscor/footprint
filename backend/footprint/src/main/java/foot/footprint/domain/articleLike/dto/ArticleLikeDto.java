package foot.footprint.domain.articleLike.dto;

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
}