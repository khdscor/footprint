package foot.footprint.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditCommentCommand {

    private final Long articleId;
    private final Long commentId;
    private final Long memberId;
    private final String content;
}