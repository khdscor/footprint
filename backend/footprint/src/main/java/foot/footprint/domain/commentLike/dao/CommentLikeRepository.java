package foot.footprint.domain.commentLike.dao;

import foot.footprint.domain.commentLike.domain.CommentLike;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeRepository {

  int saveCommentLike(CommentLike commentLike);

  List<Long> findCommentIdsILiked(Long articleId, Long memberId);
}