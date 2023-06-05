package foot.footprint.domain.articleLike.dto;

import lombok.Getter;

@Getter
public class ArticleLikeResponse {

  private boolean hasILiked;

  public ArticleLikeResponse(boolean hasILiked) {
    this.hasILiked = hasILiked;
  }
}