package foot.footprint.domain.comment.application.delete;

import foot.footprint.domain.comment.dto.DeleteCommentCommand;

public interface DeleteCommentService {

    void delete(DeleteCommentCommand command);
}
