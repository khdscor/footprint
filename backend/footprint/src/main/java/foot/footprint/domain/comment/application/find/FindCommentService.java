package foot.footprint.domain.comment.application.find;

import foot.footprint.domain.comment.dto.CommentsOnPageResponse;

public interface FindCommentService {

    CommentsOnPageResponse findComments(Long articleId, Long cursorId);
}
