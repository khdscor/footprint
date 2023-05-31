package foot.footprint.domain.comment.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeleteCommentRepository {

  @Delete("DELETE FROM comment WHERE id=#{commentId} and member_id=#{memberId}")
  int deleteComment(Long commentId, Long memberId);
}