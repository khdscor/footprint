package foot.footprint.domain.comment.application.edit;

public interface EditCommentService {

    public void edit(Long commentId, Long memberId, String newContent);
}