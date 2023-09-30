package foot.footprint.domain.comment.application.edit;

public interface EditCommentService {

    void edit(Long articleId, Long commentId, Long memberId, String newContent);
}