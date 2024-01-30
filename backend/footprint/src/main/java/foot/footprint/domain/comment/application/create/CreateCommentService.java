package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CreateCommentCommand;

public interface CreateCommentService {

    CommentResponse createComment(CreateCommentCommand command);
}