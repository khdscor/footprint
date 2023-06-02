package foot.footprint.domain.commentLike.dao;

import foot.footprint.domain.commentLike.domain.CommentLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeRepository {

  int saveCommentLike(CommentLike commentLike);
}