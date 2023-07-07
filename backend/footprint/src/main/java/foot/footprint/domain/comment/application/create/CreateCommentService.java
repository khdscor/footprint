package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.comment.dto.CommentResponse;

public interface CreateCommentService {

    CommentResponse createComment(Long articleId, String content, Long memberId);
}