package foot.footprint.domain.commentLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeCommentLikeCommand {
    private final Long commentId;
    private final Long articleId;
    private final boolean hasILiked;
    private final Long memberId;
}