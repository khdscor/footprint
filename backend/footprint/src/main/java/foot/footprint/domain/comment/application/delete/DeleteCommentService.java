package foot.footprint.domain.comment.application.delete;

import foot.footprint.domain.article.exception.NotMatchMemberException;

public interface DeleteCommentService {

    void delete(Long commentId, Long memberId);
}
