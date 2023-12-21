package foot.footprint.domain.commentLike.dao;

import foot.footprint.domain.commentLike.domain.CommentLike;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeRepository {

    int saveCommentLike(CommentLike commentLike);

    @Delete("DELETE FROM comment_like WHERE comment_id=#{commentId} and member_id=#{memberId}")
    int deleteCommentLike(Long commentId, Long memberId);
}