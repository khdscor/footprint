package foot.footprint.domain.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String content;

    public CreateCommentRequest(String content) {
        this.content = content;
    }
}