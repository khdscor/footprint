package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.comment.dto.CommentResponse;

public interface CreateCommentService {

    public CommentResponse createComment(Long articleId, String content, Long memberId);
}