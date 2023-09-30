package foot.footprint.domain.comment.application.delete;

public interface DeleteCommentService {

    void delete(Long articleId, Long commentId, Long memberId);
}
