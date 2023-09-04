package foot.footprint.domain.comment.application.find;

import foot.footprint.domain.comment.dto.CommentOnPageResponse;

public interface FindCommentService {

    CommentOnPageResponse findComments(Long articleId, Long cursorId);
}
