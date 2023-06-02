package foot.footprint.domain.commentLike.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class CommentLike {

  private Long id;
  private Long comment_id;
  private Long member_id;

  public CommentLike(Long id, Long comment_id, Long member_id) {
    this.id = id;
    this.comment_id = comment_id;
    this.member_id = member_id;
  }
}