package foot.footprint.domain.comment.application.edit;

import foot.footprint.domain.comment.dto.EditCommentCommand;

public interface EditCommentService {

    void edit(EditCommentCommand command);
}