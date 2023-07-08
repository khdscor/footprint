package foot.footprint.domain.commentLike.application;

public interface ChangeCommentLikeService {

    void changeMyLike(Long commentId, Long articleId, Boolean hasILiked, Long memberId);
}