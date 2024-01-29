package foot.footprint.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentCommand {
    Long articleId;
    String content;
    Long memberId;
}