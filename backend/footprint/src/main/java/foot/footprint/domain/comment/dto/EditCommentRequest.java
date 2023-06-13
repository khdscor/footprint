package foot.footprint.domain.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class EditCommentRequest {

    @NotEmpty(message = "빈 댓글로 수정할 수 없습니다.")
    @NotBlank(message = "빈 댓글로 수정할 수 없습니다.")
    private String content;

    public EditCommentRequest(String content) {
        this.content = content;
    }

    public EditCommentRequest() {
    }
}