package foot.footprint.domain.commentLike.dto;

import lombok.Getter;

@Getter
public class CommentLikeDto {

  Long commentLike;

  public CommentLikeDto(Long commentLike) {
    this.commentLike = commentLike;
  }
}