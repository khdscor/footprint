package foot.footprint.domain.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

  @NotEmpty(message = "빈 댓글은 작성할 수 없습니다.")
  @NotBlank(message = "빈 댓글은 작성할 수 없습니다.")
  private String content;

  public CreateCommentRequest(String content) {
    this.content = content;
  }

  public CreateCommentRequest() {
  }
}