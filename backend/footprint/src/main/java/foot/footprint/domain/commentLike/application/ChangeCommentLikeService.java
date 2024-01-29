package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.commentLike.dto.ChangeCommentLikeCommand;

public interface ChangeCommentLikeService {

    void changeMyLike(ChangeCommentLikeCommand command);
}