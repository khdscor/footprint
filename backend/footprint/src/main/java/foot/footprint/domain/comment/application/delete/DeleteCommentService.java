package foot.footprint.domain.comment.application.delete;

import foot.footprint.domain.article.exception.NotMatchMemberException;

public interface DeleteCommentService {

    public void delete(Long commentId, Long memberId);
}
